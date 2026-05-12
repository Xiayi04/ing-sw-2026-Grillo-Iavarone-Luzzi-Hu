package it.polimi.ingsw.Network.RMI;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.ServerConnection;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class ClientRMI extends UnicastRemoteObject implements RemoteClientInterface, Runnable {

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
    public void showMyTurn() throws RemoteException {
        clientController.showMyTurn();
    }

    @Override
    public void updateBoard(Board board) throws RemoteException {
        clientController.updateBoardStatus(board);
    }

    /*@Override
    public void showCurrentPlayer(String playerName) throws RemoteException {
        clientController.showCurrentPlayer(playerName);
    }*/

    @Override
    public void showError(String message) throws RemoteException {
        clientController.showError(message);
    }

    /*@Override
    public void updateOtherPlayerStatus(String playerName, List<Card> tribeCards, List<Building> buildings) throws RemoteException {
        clientController.updateOtherPlayerStatus(playerName, tribeCards, buildings);
    }*/

    @Override
    public void showMessage(String message) throws RemoteException {
        clientController.showMessage(message);
    }

    @Override
    public void showEndGame() throws RemoteException {
        clientController.showEndGame();
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


