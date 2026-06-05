package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.ClientDisconnectedException;
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

    public GameManager getGameManager() {
        return gameManager;
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

    //lobby management
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
                checkStartGame();
            }
        }

    }

    @Override
    public void checkTotem(Totem totem, VirtualClientInterface client) {

        ArrayList<TempPlayer> tempPlayers = lobby.getTempPlayers();
        synchronized (tempPlayers) {
            if (lobby.getTempPlayerByClient(client) != null &&
                    lobby.getTempPlayerByClient(client).getTempPlayerTotem() != null &&
                    lobby.getTempPlayerByClient(client).getTempPlayerTotem().equals(totem)) {
                try {
                    client.totemChoiceError();
                } catch (ClientDisconnectedException e) {
                    Server.closeConnection(client);
                }
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }//

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

    @Override
    public void connectionInitializer(VirtualClientInterface client) {
        pingManager.addClientToPingList(client);
        notifier.addClientToNotifier(client);
    }

    //Requests management

    public synchronized void moveTotemRequest(String username, int pathIndex) {
        gameManager.resolvePosition(username, pathIndex);
    }

    public synchronized void genericPick(String username, boolean isUpper, boolean isBuilding, int index, boolean skip) {
            gameManager.resolvePick(username, isUpper, isBuilding, index);
    }

    //Game Initializing

    public void gameInitializer(int numPLayers, ArrayList<TempPlayer> tempPlayers) {
        pushPlayersInGM(tempPlayers);

        setNotifier(notifier);
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
