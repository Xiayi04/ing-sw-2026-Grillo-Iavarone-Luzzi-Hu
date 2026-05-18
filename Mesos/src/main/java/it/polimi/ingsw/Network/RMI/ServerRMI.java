package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Controller.Lobby;
import it.polimi.ingsw.Controller.ServerController;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerRMI extends UnicastRemoteObject implements VirtualServer,Runnable {
    private GameManager gameManager;
    private final ServerController serverController;
    private final Lobby lobby;

    private final Map<RemoteClientInterface, VirtualClientInterface> activeClients = new ConcurrentHashMap<>();

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
            Registry registry = LocateRegistry.createRegistry(1234);//crea il registro sulla porta 1234
            registry.rebind(serverName, this);//pubblica il server nel registro
            System.out.println("---Server is ready!---");
        } catch (Exception e) {
            System.err.println("Server configuration error:  " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void connect(RemoteClientInterface virtualClient) throws RemoteException {
        VirtualClientInterface wrappedClient = new RMIVirtualClient(virtualClient);
        activeClients.put(virtualClient, wrappedClient);
        lobby.addClient(wrappedClient);

    }

    //Corpo dei metodi che stanno nel virtualServer
    @Override
    public void login(String username, Totem chosenColor, RemoteClientInterface  virtualClient ) throws RemoteException {
        lobby.addUsername(username,activeClients.get(virtualClient));
        lobby.addTotem(chosenColor,activeClients.get(virtualClient));
    }

    public synchronized void pickCard(String username, boolean isUpper,boolean isBuilding,int index) throws RemoteException{
        serverController.genericPick(username,isUpper,isBuilding,index);
    }


    @Override
    public synchronized void moveTotem(String username, int pathIndex)throws RemoteException{
        serverController.moveTotemRequest(username,pathIndex);
    }

    public synchronized void setNumPlayers (int numPlayers,RemoteClientInterface client)throws RemoteException{
        lobby.setNumPlayers(numPlayers, new RMIVirtualClient(client));
    }

    public synchronized void setTotemPosition (String username, int index) throws RemoteException{

    }

    public synchronized void leave () throws RemoteException{

    }

}
