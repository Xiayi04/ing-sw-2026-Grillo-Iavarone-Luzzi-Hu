package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Totem;
import it.polimi.ingsw.Network.ClientDisconnectedException;
import it.polimi.ingsw.Network.Notifier;
import it.polimi.ingsw.Network.Server;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.util.ArrayList;
//
public class ServerController implements LobbyManager {
    private final GameManager gameManager;
    private final Lobby lobby;
    private final PingManager pingManager = new PingManager();
    private Notifier notifier;


    public ServerController(GameManager gameManager, Notifier notifier) {
        this.gameManager = gameManager;
        this.notifier = notifier;
        this.lobby = new Lobby(this);
    }
    //setters and getters


    public PingManager getPingManager() {
        return pingManager;
    }

    public Notifier getNotifier() {
        return notifier;
    }

    public void setNotifier(Notifier notifier) {
        this.notifier = notifier;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public GameManager getGM() {
        return gameManager;
    }

    /**
     * Validates and assigns a unique username to a temporarily connected client.
     * It checks for availability within the lobby, updates the player name,
     * notifies the client, and triggers game start checks if successful.
     */
    @Override
    public void checkUsername(String username, VirtualClientInterface client) {

        ArrayList<TempPlayer> tempPlayers = lobby.getTempPlayers();

        synchronized (tempPlayers) {

            if (lobby.getTempPlayerByClient(client) != null &&
                    lobby.getTempPlayerByClient(client).getName() != null &&
                    lobby.getTempPlayerByClient(client).getName().equals(username)) {
                return;
            }
            TempPlayer tempPlayer = null;
            boolean alreadyUsed = false;

            for (TempPlayer p : tempPlayers) {
                if (p.getClient() != null && p.getClient().equals(client)) {
                    tempPlayer = p;
                }
                if (p.getName() != null && p.getName().equals(username)) {
                    alreadyUsed = true;
                }
            }

            if (!alreadyUsed && tempPlayer != null) {
                tempPlayer.setName(username);
                try {
                    client.updateConfirmedUsername(username);
                } catch (ClientDisconnectedException e) {
                    Server.terminate();
                }
                checkStartGame();
            }
            if(alreadyUsed){
                client.usernameError();
            }
        }

    }

    /**
     * Validates and assigns a unique totem to a temporary client within the lobby.
     * If the totem is available, it updates the client and triggers game start checks;
     * if already taken, it handles the conflict or rejects the connection if no totems remain.
     */
    @Override
    public void checkTotem(Totem totem, VirtualClientInterface client) {

        ArrayList<TempPlayer> tempPlayers = lobby.getTempPlayers();
        synchronized (tempPlayers) {
            if (lobby.getTempPlayerByClient(client) != null &&
                    lobby.getTempPlayerByClient(client).getTempPlayerTotem() != null &&
                    lobby.getTempPlayerByClient(client).getTempPlayerTotem().equals(totem)) {

                return;
            }

            TempPlayer tempPlayer = null;
            boolean alreadyUsed = false;

            for (TempPlayer p : tempPlayers) {
                if (p.getClient() != null && p.getClient().equals(client)) {
                    tempPlayer = p;
                }
                if (p.getTempPlayerTotem() != null && p.getTempPlayerTotem().equals(totem)) {
                    alreadyUsed = true;
                }
            }


            if (!alreadyUsed && tempPlayer != null) {
                tempPlayer.setTempPlayerTotem(totem);
                try {
                    client.updateConfirmedTotem(totem);
                } catch (ClientDisconnectedException e) {
                    handleDisconnection(client);
                }
                checkStartGame();
            } else if (alreadyUsed) {
                try {
                    if (lobby.getAvailableTotems().isEmpty()) {
                        client.totemChoiceError();
                        client.refuseConnection();
                        return;
                    }
                    client.totemChoiceError();
                } catch (ClientDisconnectedException e) {
                    Server.terminate();
                }
            }
        }
    }

    /**
     * Validates and sets the total number of players for the game,
     * restricted to the first client in the lobby.It enforces bounds (2 to 5 players),
     * updates lobby configurations, and triggers game start checks upon successful validation.
     */
    @Override
    public void checkSetNumPlayers(int numPlayers, VirtualClientInterface client) {

        ArrayList<TempPlayer> tempPlayers = lobby.getTempPlayers();
        synchronized (tempPlayers) {
            boolean present = tempPlayers.stream()
                    .map(TempPlayer::getClient)
                    .anyMatch(c -> c.equals(client));

            if (!present) {
                //errore gigante
                return;
            }
            if (!tempPlayers.getFirst().getClient().equals(client)) {
                //notFirstError
                return;
            }
            if (numPlayers < 2 || numPlayers > 5) {
                try {
                    client.numPlayersError();
                } catch (ClientDisconnectedException e) {
                    handleDisconnection(client);
                }
                return;
            }
            lobby.getNumPlayers().set(numPlayers);
            lobby.IsNumPlayersSet().set(true);
            lobby.checkMoreThenEnoughPlayers();
            checkStartGame();
            try {
                client.showChosenNumPlayers(numPlayers);
            } catch (ClientDisconnectedException e) {
                handleDisconnection(client);
            }
        }

    }

    /**
     * Verifies if all conditions to start the game are met and initializes it if ready.
     * It checks if the player count is set, the required number of clients is reached,
     * and all connected players have finalized their usernames and totems.
     */
    public void checkStartGame() {

        ArrayList<TempPlayer> tempPlayers = lobby.getTempPlayers();
        synchronized (tempPlayers) {

            if (!lobby.IsNumPlayersSet().get())
                return;

            if (lobby.getTempPlayers().size() < lobby.getNumPlayers().get())
                return;


            boolean allReady = lobby.getTempPlayers().stream()
                    .allMatch(p ->
                            p.getName() != null && p.getTempPlayerTotem() != null);

            if (!allReady) return;

            gameInitializer(lobby.getNumPlayers().get(), tempPlayers);

        }

    }

    /**
     * Registers a newly connected client into the ping and notification subsystems.
     */
    @Override
    public void connectionInitializer(VirtualClientInterface client) {
        pingManager.addClientToPingList(client);
        notifier.addClientToNotifier(client);
    }



    public synchronized void moveTotemRequest(String username, int pathIndex) {
        gameManager.resolvePosition(username, pathIndex);
    }

    public synchronized void genericPick(String username, boolean isUpper, boolean isBuilding, int index, boolean skip) {
            if(skip){
                gameManager.skipPick(username);
            }else{
                gameManager.resolvePick(username, isUpper, isBuilding, index);
            }
    }

    //Game Initializing

    /**
     * Initializes the game session with the specified player count and list of temporary
     * players.It registers the players into the game manager, configures the notifier,
     * and starts the game loop.
     */
    public void gameInitializer(int numPLayers, ArrayList<TempPlayer> tempPlayers) {
        pushPlayersInGM(tempPlayers);
        gameManager.setNumPlayers(numPLayers);
        gameManager.setNotifier(notifier);
        //necessità di far partire la partita
        gameManager.startGame();
    }

    public synchronized void addPlayerToGame(String username, Totem totem, VirtualClientInterface virtualClient) {

        Player p = new Player(username, totem, 0, virtualClient);
        synchronized (gameManager.getPlayers()) {
            gameManager.addPlayer(p);
        }
    }

    /**
     * Transfers temporary players into the primary game manager if it is currently empty.
     */
    public void pushPlayersInGM(ArrayList<TempPlayer> tempPlayers) {

        if (!gameManager.getPlayers().isEmpty()) {
            System.out.println("Players already in GM");
            return;
        }
        for (TempPlayer tempPlayer : tempPlayers) {

            addPlayerToGame(tempPlayer.getName(), tempPlayer.getTempPlayerTotem(), tempPlayer.getClient());
        }

    }

    public void handleDisconnection(VirtualClientInterface client) {
        Server.closeConnection(client);
    }

    public void closeConnections(VirtualClientInterface disconnectedClient) {
        pingManager.close();
        notifier.sendFarewell(disconnectedClient);
    }

}
