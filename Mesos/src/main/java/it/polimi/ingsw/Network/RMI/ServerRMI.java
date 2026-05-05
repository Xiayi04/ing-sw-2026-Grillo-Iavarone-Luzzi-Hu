package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Controller.Lobby;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.OfferCard;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerRMI extends UnicastRemoteObject implements VirtualServer,Runnable {
    private GameManager gameManager;
    private final ArrayList<VirtualView> clients = new ArrayList<>();

    //Costruttore
    public ServerRMI( GameManager gameManager) throws RemoteException {
        super();
        this.gameManager = gameManager;
    }

    public void run() {
        final String serverName = "---MESOS_SERVER---";
        try {
            GameManager gameManager = new GameManager(new ArrayList<Player>(),5,new Board());
            ServerRMI server = new ServerRMI(gameManager); //crea l'oggetto remoto
            Registry registry = LocateRegistry.createRegistry(1234);//crea il registro sulla porta 1234
            registry.rebind(serverName, server);//pubblica il server nel registro
            System.out.println("---Server is ready!---");
        } catch (Exception e) {
            System.err.println("Server configuration error:  " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void connect(VirtualView client) throws RemoteException {
        Lobby.addClient(new RMIVirtualClient(client));

    }

    //Corpo dei metodi che stanno nel virtualServer
    @Override
    public void login(String username, Totem chosenColor, VirtualView client) throws RemoteException{
        synchronized (this.clients){
            if (gameManager.getPlayers().size() >= 5) {
                client.showError("--FULL  GAME!--");
                return;
            }
            Player player = new Player(username, chosenColor, 0);
            gameManager.addPlayer(player);
            this.clients.add(client);
            System.out.println(username + " connected  with TOTEM :" + chosenColor);
        }
    }

    public synchronized void buyBuilding(String username,boolean isUpper,int index) throws RemoteException{
        Player p = gameManager.getPlayerByName(username);
        if( p!=null){
            gameManager.buyBuilding(p,isUpper,index);
            notifyAllClients();
        }else{
            System.out.println("Error: Player "+username+" not found!");
        }

    }

    private void notifyAllClients() {
        Board board = gameManager.getBoard();
        Player current = gameManager.getCurrentPlayer();
        String playerName;
        if(current != null){
            playerName = current.getName();
        }else{
            System.out.println("Error: Player "+gameManager.getCurrentPlayer().getName()+" not found!");
        }
        for(VirtualView client : new ArrayList<>(clients)){
            try{
                // i client osservano i cambiamenti che avvengono sul server
                client.updateBoardStatus(board);
                // i client osservano il giocatore che sta giocando
                client.showCurrentPlayer(playerName);
            } catch (Exception e) {
                System.err.println("Unable to contact a client, it may be disconnected");
                clients.remove(client);
            }
        }
    }

    @Override
    public synchronized void moveTotem(String username, int pathIndex)throws RemoteException{
        Player p = gameManager.getPlayerByName(username);
        OfferCard chosenCard = gameManager.getBoard().getPath().get(pathIndex);
        if(chosenCard.isOccupied()){
            System.out.println("The position" +pathIndex + "it's already occupied");
            return;
        }
        gameManager.getBoard().moveTotem(p,chosenCard);
        System.out.println("The player " + p.getName() + "occupied the position" + pathIndex);
        notifyAllClients();
    }

    @Override
    public synchronized void pickCard(String username, boolean isUpper,boolean isBuilding,int index) throws RemoteException{
        Player p = gameManager.getPlayerByName(username);
        try{
            if(isBuilding){
                this.buyBuilding(username,isUpper,index);
            }else{
                this.takeCharacter(username,isUpper,index);
            }
        } catch (Exception e) {
            System.err.println("Error during pickCard: " + e.getMessage());
        }

    }

    public void takeCharacter(String username, boolean isUpper, int index) throws RemoteException{
        Player player = gameManager.getPlayerByName(username);
        gameManager.takeCharacter(player,isUpper,index);
        System.out.println(username + "he took the character:" +player.getName());
        notifyAllClients();
    }

    @Override
    public synchronized void leave(String username, VirtualView client) throws RemoteException{
        clients.remove(client); //rimuove client dalla lista per le notifiche
        Player p = gameManager.getPlayerByName(username);
        if(p!=null){
            gameManager.getPlayers().remove(p);
            System.out.println("The user  " + username + "left the game ");
        }
        for (VirtualView v : new ArrayList<>(clients)){
            try{
                v.showError("The player  " + username + "he abandoned the game"); //gli altri giocatori vengono a conoscenza
            }catch(RemoteException e){
            }
        }
        notifyAllClients();
    }
}
