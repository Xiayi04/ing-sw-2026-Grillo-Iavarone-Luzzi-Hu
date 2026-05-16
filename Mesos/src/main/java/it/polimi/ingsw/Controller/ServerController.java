package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.VirtualClientInterface;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ServerController implements LobbyManager{
    private final GameManager gameManager;
    private  Notifier notifier = null;
    private final Lobby lobby;

    public ServerController(GameManager gameManager) {
        this.gameManager = gameManager;
        this.lobby = new Lobby(this);
    }

    //setters and getters
    public void setNotifier(Notifier notifier) {
        this.notifier = notifier;
    }
    public GameManager getGameManager() {
        return gameManager;
    }
    public Notifier getNotifier() {
        return notifier;
    }
    public Lobby getLobby() {
        return lobby;
    }
    public GameManager getGM(){
        return gameManager;
    }

    //lobby management
    @Override
    public void checkUsername(String username, VirtualClientInterface client){
        new Thread(() -> {
            java.util.ArrayList<TempPlayer> tempPlayers =lobby.getTempPlayers();

            synchronized (tempPlayers){
                TempPlayer tempPlayer = null;
                boolean alreadyUsed = false;

                for (TempPlayer p : tempPlayers){
                    if(p.getClient() != null && p.getClient().equals(client)){
                        tempPlayer = p;
                    }
                    if(p.getName() != null && p.getName().equals(username)){
                        alreadyUsed = true;
                    }
                }

                if(!alreadyUsed && tempPlayer != null){
                    tempPlayer.setName(username);
                    try {
                        client.confirmUsername(username);
                    } catch (Exception e) { e.printStackTrace(); }
                    checkStartGame();
                }
            }
        }).start();
    }

    @Override
    public void checkTotem(Totem totem, VirtualClientInterface client) {
        new Thread(() -> {
            ArrayList<TempPlayer> tempPlayers =lobby.getTempPlayers();
            synchronized (tempPlayers){
                TempPlayer tempPlayer = null;
                boolean alreadyUsed = false;

                for (TempPlayer p : tempPlayers){
                    if(p.getClient() != null && p.getClient().equals(client)){
                        tempPlayer = p;
                    }
                    if (p.getTempPlayerTotem() != null && p.getTempPlayerTotem().equals(totem)){
                        alreadyUsed = true;
                    }
                }


                if(!alreadyUsed && tempPlayer != null){
                    tempPlayer.setTempPlayerTotem(totem);
                    checkStartGame();
                } else if(alreadyUsed){
                    try {
                        if(lobby.getAvailableTotems().isEmpty()){
                            client.totemNotAvailableError(null);
                            client.refuseConnection();
                            return;
                        }
                        client.totemNotAvailableError(lobby.getAvailableTotems());
                    } catch (Exception e) { e.printStackTrace(); }
                }
            }
        }).start();
    }

    @Override
    public void checkSetNumPlayers(int numPlayers, VirtualClientInterface client) {
        new Thread(() -> {
            ArrayList<TempPlayer> tempPlayers =lobby.getTempPlayers();
            synchronized (tempPlayers){
                boolean present = tempPlayers.stream()
                        .map(TempPlayer::getClient)
                        .anyMatch(c->c.equals(client));

                if(!present){
                    //errore gigante
                    return;
                }
                if(!tempPlayers.getFirst().getClient().equals(client)){
                    //notFirstError
                    return;
                }
                if(numPlayers<2 || numPlayers>5){
                    //numero non valido
                    return;
                }
                lobby.getNumPlayers().set(numPlayers);
                lobby.IsNumPlayersSet().set(true);
                checkStartGame();
            }
        }).start();
    }

    public void checkStartGame() {
        new Thread(() -> {
            ArrayList<TempPlayer> tempPlayers =lobby.getTempPlayers();
            synchronized (tempPlayers){

                if(!lobby.IsNumPlayersSet().get())
                    return;

                if(lobby.getTempPlayers().size()<lobby.getNumPlayers().get())
                    return;


                boolean allReady = lobby.getTempPlayers().stream()
                        .allMatch(p ->
                                p.getName() != null &&
                                        p.getTempPlayerTotem() != null);

                if (!allReady) return;

                gameInitializer(lobby.getNumPlayers().get() , tempPlayers);
            }
        }).start();
    }

    //Requests management

    public synchronized void moveTotemRequest(String username, int pathIndex)throws RemoteException {
        new Thread( () ->{
            gameManager.resolvePosition(username, pathIndex);
        }).start();
    }

    public synchronized void genericPick(String username, boolean isUpper,boolean isBuilding,int index){
        new Thread(()->{
            gameManager.resolvePick(username, isUpper,isBuilding,index);
        }).start();
    }

    //Game Initializing

    public  void gameInitializer(int numPLayers, ArrayList<TempPlayer> tempPlayers){
        new Thread(()->{
            gameManager.setNumPlayers(numPLayers);
            pushPlayersInGM(tempPlayers);

            synchronized (gameManager.getPlayers()){
                while(gameManager.getPlayers().size()<numPLayers){
                    try {
                        gameManager.getPlayers().wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                ArrayList<VirtualClientInterface> clients = new ArrayList<>();
                for(TempPlayer p : tempPlayers){
                    clients.add(p.getClient());
                }
                Notifier notifier = new Notifier(clients);
                setNotifier(notifier);
                gameManager.setNotifier(notifier)


            }
        }).start();
    }

    public synchronized void addPlayerToGame(String username, Totem totem, VirtualClientInterface virtualClient) {

        Player p = new Player(username,totem,0 ,virtualClient);
        synchronized (gameManager.getPlayers()){
            gameManager.addPlayer(p);
        }
        notifier.addedNewPlayerBroadcast(p);
    }

    public void pushPlayersInGM(ArrayList<TempPlayer> tempPlayers){
        new Thread( () ->{
            synchronized (gameManager.getPlayers()) {
                if(!gameManager.getPlayers().isEmpty()){
                    throw new RuntimeException("Players already in GM");
                }
                for(TempPlayer tempPlayer : tempPlayers){

                    new Thread( () ->{
                        addPlayerToGame(tempPlayer.getName(), tempPlayer.getTempPlayerTotem(), tempPlayer.getClient());
                    }).start();
                }
                gameManager.getPlayers().notifyAll();
            }
        }).start();
    }




}
