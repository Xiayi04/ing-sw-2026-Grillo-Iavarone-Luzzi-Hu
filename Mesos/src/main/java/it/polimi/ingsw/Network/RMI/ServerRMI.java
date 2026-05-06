package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Controller.Lobby;
import it.polimi.ingsw.Controller.ServerController;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.OfferCard;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerRMI extends UnicastRemoteObject implements VirtualServer,Runnable {
    private GameManager gameManager;
    private final ServerController serverController;
    private final Lobby lobby;

    //Costruttore
    public ServerRMI( GameManager gameManager, ServerController serverController, Lobby lobby) throws RemoteException {
        super();
        this.gameManager = gameManager;
        this.serverController = serverController;
        this.lobby = lobby;
    }

    public void run() {
        try {
            final String serverName = "---MESOS_SERVER---";
            //GameManager gameManager = new GameManager(new ArrayList<Player>(),5,new Board());
            //ServerRMI server = new ServerRMI(gameManager); //crea l'oggetto remoto
            Registry registry = LocateRegistry.createRegistry(1234);//crea il registro sulla porta 1234
            registry.rebind(serverName, this);//pubblica il server nel registro
            System.out.println("---Server is ready!---");
        } catch (Exception e) {
            System.err.println("Server configuration error:  " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void connect(RemoteClientInterface client) throws RemoteException {
        lobby.addClient(new RMIVirtualClient(client));

    }

    //Corpo dei metodi che stanno nel virtualServer
    @Override
    public void login(String username, Totem chosenColor, RemoteClientInterface  client ) throws RemoteException {
        serverController.addPlayerToGame(username, chosenColor.toString(), client);
    }

    public synchronized void buyBuilding(String playerName,boolean isUpper,int index) throws RemoteException{
       serverController.buyBuilding(playerName , isUpper, index);

    }


    @Override
    public synchronized void moveTotem(String username, int pathIndex)throws RemoteException{
        serverController.moveTotem(username,pathIndex);
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
        serverController.takeCharacter(username, isUpper, index);
    }

}
