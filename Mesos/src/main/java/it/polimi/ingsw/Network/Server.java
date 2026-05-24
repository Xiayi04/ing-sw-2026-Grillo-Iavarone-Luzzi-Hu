package it.polimi.ingsw.Network;

import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Controller.Lobby;
import it.polimi.ingsw.Controller.Notifier;
import it.polimi.ingsw.Controller.ServerController;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Network.RMI.ServerRMI;
import it.polimi.ingsw.Network.Socket.Server.ServerSocket;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Server{
    public static AtomicInteger numConnections = new AtomicInteger(0);
    public static Object lock = new Object();
    static int rmiPort;
    static int socketPort=777;
    static String host;
    static GameManager gameManager;
    private static ServerRMI serverRMI;
    private static ServerSocket serverSocket;
    static ServerController serverController;
    static final AtomicBoolean isTerminationStarted = new AtomicBoolean(false);


    public static void main(String[] args) throws RemoteException {


        System.out.println("Server starting...");

        GameManager gameManager = new GameManager(null,0,new Board());
        serverController = new ServerController(gameManager);

        serverSocket = new ServerSocket(socketPort,serverController,serverController.getLobby());
        serverRMI = new ServerRMI(gameManager,serverController,serverController.getLobby());

        Thread serverRMIThread = new Thread(serverRMI);
        Thread serverSocketThread = new Thread(serverSocket);
        serverRMIThread.start();
        serverSocketThread.start();


    }


    public synchronized static void terminate(){
        if(isTerminationStarted.get()){
            return;
        }
        isTerminationStarted.set(true);

        try {
            serverSocket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //serverRMI.close();
    }
}
