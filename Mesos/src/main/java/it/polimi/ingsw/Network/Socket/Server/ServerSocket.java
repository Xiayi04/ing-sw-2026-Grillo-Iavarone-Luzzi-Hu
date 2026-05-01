package it.polimi.ingsw.Network.Socket.Server;

import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Network.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerSocket implements Runnable {
    private final Integer port;
    private final GameManager gameManager;
    public static Object readerLock = new Object();
    public static Object writerLock = new Object();

    public ServerSocket(Integer port, GameManager gameManager) {
        this.port = port;
        this.gameManager= gameManager;
    }


    @Override
    public void run() {
        System.out.println("SocketServer starting...");
        try {
            java.net.ServerSocket serverSocket = new java.net.ServerSocket(port);
            System.out.println("SocketServer started...");

            while (true) {
                Socket socket = serverSocket.accept();
                ClientSocketProxy proxy = new ClientSocketProxy(socket, gameManager);
                boolean accepted = false;

                synchronized (Server.class) {
                    if(Server.numConnections.get() >= gameManager.getNumPlayers()) {
                        accepted = false;
                    }else {
                        accepted = true;
                        Server.numConnections.incrementAndGet();
                        Server.clientProxies.add(proxy);
                        Server.lock.notify();
                        proxy.askForLogin(gameManager.getAvailableTotems());
                    }
                }

                if (!accepted) {

                    try {
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        out.println("Server already full");
                        socket.close();
                        continue;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }

        }catch (Exception e){

        }
    }
}
