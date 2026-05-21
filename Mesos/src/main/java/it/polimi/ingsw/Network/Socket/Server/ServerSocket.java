package it.polimi.ingsw.Network.Socket.Server;

import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Controller.ServerController;
import it.polimi.ingsw.Controller.Lobby;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ServerSocket implements Runnable,AutoCloseable {
    private final Integer port;
    private final ServerController serverController;
    private final GameManager gameManager;
    private final Lobby lobby;
    public static Object readerLock = new Object();
    public static Object writerLock = new Object();
    public boolean termination = false;
    public java.net.ServerSocket  serverSocket;
    public final ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

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
            serverSocket = new java.net.ServerSocket(8000);
            System.out.println("SocketServer started...");

            while (!termination) {
                Socket socket = serverSocket.accept();
                VirtualClientInterface proxy = new SocketVirtualClient(socket, gameManager);
                lobby.addClient(proxy);
                clientHandlers.add(new ClientHandler(socket, gameManager, proxy, serverController));
                System.out.println("Accepted connection from " + socket);

            }

        }catch (Exception e){

        }
    }

    @Override
    public void close() throws Exception {
        System.out.println("SocketServer closing...");
        termination = true;

        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
                System.out.println("SocketServer closed");
            } catch (IOException e) {
                throw new RuntimeException("SocketServer error while closing", e);
            }
        }


        if(!clientHandlers.isEmpty()){
            for (ClientHandler clientHandler : clientHandlers) {

                try {
                    if(clientHandler!=null)
                        clientHandler.close();
                } catch (Exception e) {
                    throw new RuntimeException("Error closing a ClientHandler", e);
                }
            }
        }
        clientHandlers.clear();
    }
}
