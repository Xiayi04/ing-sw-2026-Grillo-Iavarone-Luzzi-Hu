package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Controller.Lobby;

import it.polimi.ingsw.Controller.ServerController;
import it.polimi.ingsw.Model.Game.Totem;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerRMI extends UnicastRemoteObject implements VirtualServer,Runnable {
    private final GameManager gameManager;
    private final ServerController serverController;
    private final Lobby lobby;

    private static final Map<RemoteClientInterface, VirtualClientInterface> activeClients = new ConcurrentHashMap<>();

    //Costruttore
    public ServerRMI(GameManager gameManager,ServerController serverController, Lobby lobby) throws RemoteException {
        super();
        this.gameManager = gameManager;
        this.serverController = serverController;
        this.lobby = lobby;

    }


    /**
     * Initializes and starts the RMI server.
     * This method creates an RMI registry on port 1234 and binds the current
     * server instance to the specified name ({@code ---MESOS_SERVER---}),
     * making it available for remote clients to look up and invoke.
     *
     */
    public void run() {
        try {
            final String serverName = "---MESOS_SERVER---";
            //System.setProperty("java.rmi.server.hostname","192.168.1.230");
            //It sets the registry to port 1234
            Registry registry = LocateRegistry.createRegistry(1234);

            registry.rebind(serverName, this);
            System.out.println("Server RMI started...");
        } catch (Exception e) {
            System.err.println("Server configuration error:  " + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * Establishes a new connection for an RMI client.
     * This method wraps the provided {@link RemoteClientInterface} into an
     * {@link RMIVirtualClient}, stores it in the active clients collection,
     * and registers it within the game lobby.
     *
     * @param virtualClient The RMI stub of the client requesting to connect.
     * @throws RemoteException If a communication error occurs during the RMI call.
     */
    @Override
    public void connect(RemoteClientInterface virtualClient) throws RemoteException {
        VirtualClientInterface wrappedClient = new RMIVirtualClient(virtualClient);
        activeClients.put(virtualClient, wrappedClient);
        lobby.addClient(wrappedClient);
        System.out.println("\n Nuovo client RMI connesso e inserito in Lobby.");

    }

    /**
     * Handles the login request for a client, associating a username and a chosen totem.
     * The method verifies if the client is already registered in the active session.
     * If valid, it proceeds to register the username and the selected totem in the game lobby.
     * If the client is not found, an error message is logged to the standard error stream.
     *
     * @param username     The unique identifier chosen by the player.
     * @param chosenColor  The {@link Totem} object representing the player's color choice.
     * @param virtualClient The RMI stub of the client attempting to log in.
     * @throws RemoteException If a communication error occurs during the RMI call.
     */
    @Override
    public void login(String username, Totem chosenColor, RemoteClientInterface  virtualClient ) throws RemoteException {
        VirtualClientInterface wrappedClient =activeClients.get(virtualClient);
        if(wrappedClient != null) {
            System.out.println("ServerRMI: Username received -> " + username);
            System.out.println("ServerRMI: Totem received    -> " + chosenColor);
            lobby.addUsername(username, wrappedClient);
            lobby.addTotem(chosenColor, wrappedClient);

        }
        else {
            System.err.println("ServerRMI: Invalid login request (missing or incorrect parameters).");
        }
    }

    /**
     * Handles the request for a player to pick a card during their turn.
     * This method delegates the card selection logic to the {@code serverController},
     * forwarding the user's identification, the card's position parameters,
     * and the type of deck from which the card is being drawn.
     *
     * @param username    The identifier of the player requesting to pick a card.
     * @param isUpper     {@code true} if picking from the upper level/row, {@code false} otherwise.
     * @param isBuilding  {@code true} if picking from the building deck, {@code false} otherwise.
     * @param index       The specific index of the card to be picked.
     * @throws RemoteException If a communication error occurs during the RMI call.
     */
    @Override
    public synchronized void requestPickCardManagement(String username, boolean isUpper,boolean isBuilding,int index, boolean skip) throws RemoteException{
        serverController.genericPick(username,isUpper,isBuilding,index,skip);
    }

    /**
     * Handles the request to move a totem within the game path for a specific user.
     * This method delegates the movement logic to the {@code serverController},
     * passing the username and the target path index.
     *
     * @param username  The identifier of the player requesting the move.
     * @param pathIndex The target index in the path where the totem should be moved.
     * @throws RemoteException If a communication error occurs during the RMI call.
     */
    @Override
    public synchronized void requestMoveTotemManagement(String username, int pathIndex)throws RemoteException{
        serverController.moveTotemRequest(username,pathIndex);
    }

    /**
     * Handles the request to set the number of players in the game lobby.
     * This method retrieves the {@code VirtualClientInterface} ssociated with the provided
     * client stub. If the client is already registered as active, it uses the existing wrapper;
     * otherwise, it creates a new {@link RMIVirtualClient} to handle the request.
     *
     * @param numPlayers      The desired number of players for the game.
     * @param client          The RMI stub of the client requesting the configuration.
     * @throws RemoteException  If a communication error occurs during the RMI call,.
     */
    @Override
    public synchronized void requestSetNumPlayersManagement (int numPlayers,RemoteClientInterface client)throws RemoteException{
        VirtualClientInterface wrapped =  activeClients.get(client);
        if(wrapped != null) {
            lobby.setNumPlayers(numPlayers,wrapped);
            System.out.printf("numPlayers is set" + numPlayers);
        }else{
            VirtualClientInterface newClient = new RMIVirtualClient(client);
            lobby.setNumPlayers(numPlayers,newClient);
        }
    }


    /**
     * Handles the voluntary disconnection of a client from the server.
     * This method retrieves the {@code VirtualClientInterface} associated with the
     * client stub, removing it from the collection of active clients.
     * The disconnection is then delegated to the {@code serverController}.
     *
     * @param client The RMI stub of the client requesting the disconnection.
     * @throws RemoteException If a communication error occurs during the RMI call.
     * @throws RuntimeException If the provided client is not present in the
     * active clients collection.
     */
    @Override
    public synchronized void leave (RemoteClientInterface client) throws RemoteException{
        VirtualClientInterface wrappedClient =activeClients.remove(client);
        if(wrappedClient == null) {
            throw new RuntimeException("Tentativo di disconnessione per un utente non presente.");
        }
        serverController.handleDisconnection(wrappedClient);

    }


    /**
     * Handles the management request for available totems (player colors)
     * for a specific client.
     * This method identifies the {@code VirtualClientInterface} associated
     * with the first totem in the provided list and triggers the lobby
     * to send the list of available colors to that client.
     * @param client {@link ArrayList} of {@link Totem} objects
     *      * representing the available choices.
     */
   @Override
    public void requestAvailableTotemsManagement(RemoteClientInterface client){
        VirtualClientInterface wrappedClient =activeClients.get(client);
        lobby.sendAvailableColors(wrappedClient);
    }

    @Override
    public void ping() throws RemoteException{

    }

    /**
     * Closes the RMI module by unexporting the remote object and unbinding it
     * from the RMI registry.
     * The method performs the following steps:
     * -Unexports the remote object ({@code this}), forcing the termination of
     * active connections to prevent system deadlocks.
     * -Removes the server reference from the RMI registry (port 1234).
     * -Clears all local collections of active clients.
     *
     * If the object is already unexported, the {@link java.rmi.NoSuchObjectException}
     * is caught and ignored.
     */

    public void closeRMI(){
        try {
            java.rmi.server.UnicastRemoteObject.unexportObject(this, true);
            java.rmi.registry.Registry registry = java.rmi.registry.LocateRegistry.getRegistry(1234);
            registry.unbind("---MESOS_SERVER---");
            activeClients.clear();

            System.out.println("RMI module closed successfully.");
        } catch (java.rmi.NoSuchObjectException e) {
            // Happens if the server is already closed, safe to ignore.
        } catch (Exception e) {
            System.err.println("Error while closing RMI : " + e.getMessage());
        }
    }




}
