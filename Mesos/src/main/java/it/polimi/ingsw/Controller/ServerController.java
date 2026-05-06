package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Game.OfferCard;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.RMI.ClientRMI;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.rmi.RemoteException;
import java.util.ArrayList;

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

    public void takeCharacter(String username, boolean isUpper, int index) throws RemoteException{
        Player player = gameManager.getPlayerByName(username);
        gameManager.takeCharacter(player,isUpper,index);
        System.out.println(username + "he took the character:" +player.getName());
    }

    public synchronized void buyBuilding(String username,boolean isUpper,int index) throws RemoteException {
        Player p = gameManager.getPlayerByName(username);
        if( p!=null){
            gameManager.buyBuilding(p,isUpper,index);
        }else{
            System.out.println("Error: Player "+username+" not found!");
        }

    }

    public synchronized void pickCard(String playerUsername, boolean isUpper, boolean isBuilding, int index){
        synchronized (GameManager.class){
            Player p = gameManager.getPlayerByName(playerUsername);
            try{
                if(isBuilding){
                    this.buyBuilding(playerUsername,isUpper,index);
                }else{
                    this.takeCharacter(playerUsername,isUpper,index);
                }
            } catch (Exception e) {
                System.err.println("Error during pickCard: " + e.getMessage());
            }

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
    public synchronized void moveTotem(String username, int pathIndex)throws RemoteException {
        synchronized (this.clients) {
            Player p = gameManager.getPlayerByName(username);
            OfferCard chosenCard = gameManager.getBoard().getPath().get(pathIndex);
            if (chosenCard.isOccupied()) {
                System.out.println("The position" + pathIndex + "it's already occupied");
                return;
            }
            gameManager.getBoard().moveTotem(p, chosenCard);
            System.out.println("The player " + p.getName() + "occupied the position" + pathIndex);
        }
    }


    public void totemMove(VirtualClientInterface virtualClient){
        //synchronized (){}
    }

}
