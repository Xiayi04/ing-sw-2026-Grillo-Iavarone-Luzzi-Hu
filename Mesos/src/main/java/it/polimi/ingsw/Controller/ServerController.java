package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.ClientDisconnectedException;
import it.polimi.ingsw.Network.Server;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ServerController implements LobbyManager {
    private final GameManager gameManager;
    private final Lobby lobby;
    private Notifier notifier = null;

    public ServerController(GameManager gameManager) {
        this.gameManager = gameManager;
        this.lobby = new Lobby(this);
    }
    //setters and getters

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
                    lobby.getTempPlayerByClient(client).getName()!=null &&
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
                new Thread(this::checkStartGame);
            }
        }

    }

    @Override
    public void checkTotem(Totem totem, VirtualClientInterface client) {

        ArrayList<TempPlayer> tempPlayers = lobby.getTempPlayers();
        synchronized (tempPlayers) {
            if (lobby.getTempPlayerByClient(client) != null &&
                    lobby.getTempPlayerByClient(client).getTempPlayerTotem()!= null &&
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
                } catch (RemoteException | ClientDisconnectedException e) {
                    handleDisconnection(client);
                } catch (IOException e) {
                    throw new RuntimeException("Other type of error in connection");
                }
                new Thread(this::checkStartGame);
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

    }

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
                } catch (IOException e) {
                    //throw new RuntimeException(e);
                }
                return;
            }
            lobby.getNumPlayers().set(numPlayers);
            lobby.IsNumPlayersSet().set(true);
            new Thread(this::checkStartGame);
            try {
                client.showChosenNumPlayers(numPlayers);
            } catch (RemoteException | ClientDisconnectedException e) {
                handleDisconnection(client);
            } catch (IOException e) {
                throw new RuntimeException("Other type of error in connection");
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
                            p.getName() != null &&
                                    p.getTempPlayerTotem() != null);

            if (!allReady) return;

            new Thread(() -> {
                gameInitializer(lobby.getNumPlayers().get(), tempPlayers);
            }).start();
        }

    }

    //Requests management

    public synchronized void moveTotemRequest(String username, int pathIndex) throws RemoteException {
        new Thread(() -> {
            gameManager.resolvePosition(username, pathIndex);
        }).start();
    }

    public synchronized void genericPick(String username, boolean isUpper, boolean isBuilding, int index) {

            try {
                gameManager.resolvePick(username, isUpper, isBuilding, index);
            } catch (IOException e) {
                //handleDisconnection();
            }

    }

    //Game Initializing

    public void gameInitializer(int numPLayers, ArrayList<TempPlayer> tempPlayers) {

        //gameManager.setNumPlayers(numPLayers);
        pushPlayersInGM(tempPlayers);

        synchronized (gameManager.getPlayers()) {
            while (gameManager.getPlayers().size() < numPLayers) {
                try {
                    gameManager.getPlayers().wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            ArrayList<VirtualClientInterface> clients = new ArrayList<>();

            for (TempPlayer p : tempPlayers) {
                clients.add(p.getClient());
            }
            Notifier notifier = new Notifier(clients);
            setNotifier(notifier);
            gameManager.setNotifier(notifier);
            //necessità di far partire la partita
            try {
                gameManager.startGame();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public synchronized void addPlayerToGame(String username, Totem totem, VirtualClientInterface virtualClient) {

        Player p = new Player(username, totem, 0, virtualClient);
        synchronized (gameManager.getPlayers()) {
            gameManager.addPlayer(p);
        }
    }

    public void pushPlayersInGM(ArrayList<TempPlayer> tempPlayers) {
        new Thread(() -> {
            synchronized (gameManager.getPlayers()) {
                if (!gameManager.getPlayers().isEmpty()) {
                    throw new RuntimeException("Players already in GM");
                }
                for (TempPlayer tempPlayer : tempPlayers) {

                    new Thread(() -> {
                        addPlayerToGame(tempPlayer.getName(), tempPlayer.getTempPlayerTotem(), tempPlayer.getClient());
                    }).start();
                }
                gameManager.getPlayers().notifyAll();
            }
        }).start();
    }

    public void handleDisconnection(VirtualClientInterface client) {
        Server.terminate();
    }

}
