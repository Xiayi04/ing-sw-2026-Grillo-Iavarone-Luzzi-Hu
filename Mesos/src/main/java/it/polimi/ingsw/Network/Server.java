package it.polimi.ingsw.Network;

import it.polimi.ingsw.Controller.GameManager;
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
        gameManager = new GameManager(new ArrayList<Player>(),1, new Board());

        System.out.println("Server starting...");

        askNumPlayersToFirst();

        new Thread(new ServerSocket(socketPort, gameManager)).start();
        new Thread(new ServerRMI(gameManager)).start();



    }

    /**
     *  askNumPlayersToFirst is a method that waits for the first player to join the game.
     *  This occurs when Server.numConnections increases from 0 to 1 and the first player's proxy is added to Server.clientProxies
     *  When these changes wake up the thread, it calls askForNumPlayers using the client’s proxy.
     *  The thread then waits for an edit of gameManager.numPlayers before its end so Server.class remains locked e no other player
     *  can be added to the game
     */
    public static void askNumPlayersToFirst(){
        Thread t = new Thread( ()->{
            synchronized (Server.lock){
                while(!(Server.numConnections.get()==1)){
                    try {
                        Server.lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                ClientProxy proxy = Server.clientProxies.get(0);
                synchronized (gameManager.numPlayersLock){
                    proxy.askForNumPlayers();
                    while(gameManager.getNumPlayers()<=1){
                        try {
                            gameManager.numPlayersLock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
        t.start();

    }
}
