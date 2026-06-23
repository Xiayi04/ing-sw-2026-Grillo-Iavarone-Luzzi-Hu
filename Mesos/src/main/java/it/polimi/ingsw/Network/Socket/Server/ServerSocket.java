package it.polimi.ingsw.Network.Socket.Server;

import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Controller.ServerController;
import it.polimi.ingsw.Controller.Lobby;
import it.polimi.ingsw.Network.VirtualClientInterface;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ServerSocket implements Runnable,AutoCloseable {
    private final ServerController serverController;
    private final GameManager gameManager;
    private final Lobby lobby;
    public boolean termination = false;
    public java.net.ServerSocket  serverSocket;
    public final ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    public ServerSocket(ServerController serverController) {
        this.serverController = serverController;
        this.gameManager= serverController.getGM();
        this.lobby = serverController.getLobby();
    }

    /**
     * Starts the network socket server loop on port 8000 and listens for incoming connections.
     * It continuously accepts sockets, instantiates virtual client proxies,
     * registers them in the lobby, and delegates handling tasks.
     */
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
                clientHandlers.add(new ClientHandler(socket, proxy, serverController));
                System.out.println("Accepted connection from " + socket);
            }

        }catch (Exception e){
            System.out.println("SocketServer stopping : " + e.getMessage());
        }
    }

    @Override
    public void close(){
        System.out.println("SocketServer closing...");
        termination = true;

        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
                System.out.println("SocketServer closed");
            } catch (IOException e) {
                System.out.println("SocketServer closed IOException: " + e.getMessage());
            }
        }


        if(!clientHandlers.isEmpty()){
            for (ClientHandler clientHandler : clientHandlers) {

                try {
                    if(clientHandler!=null)
                        clientHandler.close();
                } catch (Exception e) {
                    System.out.println("Error while closing client handler: " + e.getMessage());
                }
            }
        }
        clientHandlers.clear();
    }
}
