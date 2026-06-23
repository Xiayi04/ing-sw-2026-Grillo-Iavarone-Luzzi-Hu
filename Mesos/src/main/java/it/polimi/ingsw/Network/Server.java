package it.polimi.ingsw.Network;

import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Controller.ServerController;
import it.polimi.ingsw.Database.DatabaseManager;
import it.polimi.ingsw.Model.Game.Board;
import it.polimi.ingsw.Network.RMI.ServerRMI;
import it.polimi.ingsw.Network.Socket.Server.ServerSocket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server{
    private static ServerRMI serverRMI;
    private static ServerSocket serverSocket;
    static ServerController serverController;
    static final AtomicBoolean isTerminationStarted = new AtomicBoolean(false);

    /**
     * Configures database, initializes game state, and starts RMI and Socket server threads.
     */
    public static void main(String[] args) throws RemoteException {
        System.out.println("Server starting...");
        DatabaseManager.createTable();
        GameManager gameManager = new GameManager(new ArrayList<>(),0, new Board());
        serverController = new ServerController(gameManager, new Notifier());

        serverSocket = new ServerSocket(serverController);
        serverRMI = new ServerRMI(gameManager,serverController,serverController.getLobby());

        Thread serverRMIThread = new Thread(serverRMI);
        Thread serverSocketThread = new Thread(serverSocket);
        serverRMIThread.start();
        serverSocketThread.start();
    }

    /**
     * Safely shuts down the server by closing socket and RMI connections,
     * then exits the application.This method ensures thread-safe,
     * single-execution termination logic and includes a short delay before exit.
     */
    public synchronized static void terminate(){
        if(isTerminationStarted.get()){
            return;
        }
        isTerminationStarted.set(true);

        serverSocket.close();
        serverRMI.closeRMI();

        System.out.println("The server has been terminated. The program will soon close");
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(5));
        } catch (InterruptedException e) {
            System.out.println("Error while sleeping:" + e.getMessage());
        }
        System.exit(0);
    }

    public synchronized static void closeConnection(VirtualClientInterface disconnectedClient){
        serverController.closeConnections(disconnectedClient);
    }
}
