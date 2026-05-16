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
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    public static AtomicInteger numConnections = new AtomicInteger(0);
    public static Object lock = new Object();
    static int rmiPort;
    static int socketPort;
    static String host;
    static GameManager gameManager;
    public static ArrayList<ClientProxy> clientProxies = new ArrayList<>();


    public static void main(String[] args) throws RemoteException {


        System.out.println("Server starting...");

        Lobby lobby = new Lobby();
        GameManager gameManager = new GameManager(null,0,new Board());
        ServerController serverController = new ServerController(lobby,gameManager);


        new Thread(new ServerSocket(socketPort,serverController, lobby)).start();
        new Thread(new ServerRMI(gameManager,serverController,lobby)).start();



    }


}
