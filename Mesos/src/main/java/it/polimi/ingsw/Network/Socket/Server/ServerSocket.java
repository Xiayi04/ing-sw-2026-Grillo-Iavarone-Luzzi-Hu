package it.polimi.ingsw.Network.Socket.Server;

import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Controller.ServerController;
import it.polimi.ingsw.Controller.Lobby;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.net.Socket;

public class ServerSocket implements Runnable {
    private final Integer port;
    private final ServerController serverController;
    private final GameManager gameManager;
    private final Lobby lobby;
    public static Object readerLock = new Object();
    public static Object writerLock = new Object();

    public ServerSocket(Integer port, ServerController serverController, Lobby lobby) {
        this.port = port;
        this.serverController = serverController;
        this.gameManager= serverController.getGM();
        this.lobby = lobby;
    }


    @Override
    public void run() {
        System.out.println("SocketServer starting...");
        try {
            java.net.ServerSocket serverSocket = new java.net.ServerSocket(port);
            System.out.println("SocketServer started...");

            while (true) {
                Socket socket = serverSocket.accept();
                VirtualClientInterface proxy = new SocketVirtualClient(socket, gameManager);
                new ClientHandler(socket, gameManager, proxy, serverController);
                System.out.println("Accepted connection from " + socket.getInetAddress());


            }

        }catch (Exception e){

        }
    }
}
