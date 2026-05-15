package it.polimi.ingsw.Network.RMI;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.ServerConnection;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class ClientRMI extends UnicastRemoteObject implements
        RemoteClientInterface,ServerConnection, Runnable {

    private ClientController clientController;
    private VirtualServer server;
    private final String localHost;
    private final int port;
    private final String serverName;

    public ClientRMI(String localHost, int port, String serverName) throws RemoteException {
        super();
        this.localHost = localHost;
        this.port = port;
        this.serverName = serverName;
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void showStartGame() throws RemoteException {
        clientController.showStartGame();
    }


    @Override
    public void showPlayerTurn(Player player) throws RemoteException {
        clientController.showPlayerTurn(player);
    }
    @Override
    public void addedPlayer(Player player) throws RemoteException {
        clientController.addedPlayer(player);
    }

    @Override
    public void showPickedCard(Player playerWhoPicked, boolean row, boolean isBuilding, int index)
            throws RemoteException {
        clientController.showPickedCard(playerWhoPicked, row, isBuilding, index);
    }
    @Override
    public void movedTotem(Player player, int path) throws RemoteException {
        clientController.movedTotem(player, path);
    }

    @Override
    public void foodUpdated(Player player, int foodUpdated) throws RemoteException {
        clientController.foodUpdated(player, foodUpdated);
    }

    @Override
    public void updatePlayerPP(Player player, int ppUpdated) throws RemoteException {
        clientController.updatePlayerPP(player, ppUpdated);
    }
    @Override
    public void showErrorMessage(String message) throws RemoteException {
        clientController.showErrorMessage(message);
    }

    @Override
    public void showEndGame() throws RemoteException {
        clientController.showEndGame();
    }
    //implementazione serverconnection
    @Override
    public void login(String username, Totem chosenTotem) {
        try {
            server.login(username, chosenTotem, this);
        } catch (RemoteException e) {
            clientController.showErrorMessage("RMI error during login.");
        }
    }

    @Override
    public void setNumPlayers(int numPlayer) {
        try {
            server.setNumPlayers(numPlayer);
        } catch (RemoteException e) {
            clientController.showErrorMessage("RMI error while setting number of players.");
        }
    }
    @Override
    public void pickCard(String username, boolean isUpper, boolean isBuilding, int index) {
        try {
            server.pickCard(username, isUpper, isBuilding, index);
        } catch (RemoteException e) {
            clientController.showErrorMessage("RMI error while picking card.");
        }
    }

    @Override
    public void setTotemPosition(int chosenPosition) {
        try {
            server.setTotemPosition(chosenPosition);
        } catch (RemoteException e) {
            clientController.showErrorMessage("RMI error while setting totem position.");
        }
    }
    @Override
    public void leave() {
        try {
            server.leave();
        } catch (RemoteException e) {
            clientController.showErrorMessage("RMI error while leaving the game.");
        }
    }

    @Override
    public void run() {
        try {
            Registry registry = LocateRegistry.getRegistry(localHost, port);
            server = (VirtualServer) registry.lookup(serverName);

            System.out.println("Connected to RMI server.");

        } catch (Exception e) {
            System.out.println("Cannot connect to RMI server.");
            e.printStackTrace();
        }
    }


}


