package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.VirtualClientInterface;

public class ServerController {
    private final GameManager gameManager;
    private final Notifier notifier;

    public ServerController(GameManager gameManager,  Notifier notifier) {
        this.gameManager = gameManager;
        this.notifier = notifier;
        gameStarter();
    }

    public void gameStarter(){
        new Thread(()->{

            while(true){
                int numPlayers;
                synchronized (GameManager.numPlayersLock) {
                    numPlayers = gameManager.getNumPlayers();
                    if(numPlayers<2) {
                        GameManager.numPlayersLock.notifyAll();
                        continue;
                    }
                    GameManager.numPlayersLock.notifyAll();
                }
                GameManager.numPlayersLock.notifyAll();


                synchronized (gameManager.getPlayers()) {

                    if(gameManager.getNumPlayers()==numPlayers){
                        //gameManager.gameInitializing();
                        gameManager.getPlayers().notifyAll();
                        break;
                    }
                    gameManager.getPlayers().notifyAll();
                }
            }
        }).start();
    }

    public GameManager getGM(){
        return gameManager;
    }

    public synchronized void addPlayerToGame(String username, String totem, VirtualClientInterface virtualClient) {
        synchronized (Lobby.getClients()){
            if(!Lobby.getClients().contains(virtualClient)){
                throw new RuntimeException("Error: virtualClient not present in lobby!");
            }
        }
        totem = totem.toUpperCase();
        Player p = new Player(username,Totem.valueOf(totem),0 ,virtualClient);
        synchronized (GameManager.playersLock){
            gameManager.getPlayers().add(p);
            GameManager.playersLock.notifyAll();
        }

        notifier.addedNewPlayerBroadcast(p);
    }

    public synchronized void pickCard(String playerUsername, boolean isUpper, int index){
        synchronized (GameManager.class){

        }
    }
    //dubbio
    public void refuseConnection(VirtualClientInterface virtualClient){

    }

    public void setNumPlayers(int numPlayers){
        synchronized (GameManager.numPlayersLock){
            gameManager.setNumPlayers(numPlayers);
            Lobby.isNumPlayersSetted = true;
            GameManager.numPlayersLock.notifyAll();
        }

            Lobby.isNumPlayersSetted = true;



    }

    public void totemMove(VirtualClientInterface virtualClient){
        //synchronized (){}
    }

}
