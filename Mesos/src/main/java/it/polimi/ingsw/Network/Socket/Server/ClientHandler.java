package it.polimi.ingsw.Network.Socket.Server;
import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Controller.ServerController;
import it.polimi.ingsw.Network.Server;
import it.polimi.ingsw.Network.Socket.Client.MessageFromClient;
import it.polimi.ingsw.Network.Socket.Server.Command.ServerCommand;
import it.polimi.ingsw.Network.Socket.Server.Command.CommandFactoryServer;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler implements Runnable, AutoCloseable {
    private final Socket socket;
    private final ObjectInputStream reader;
    VirtualClientInterface client;
    public Boolean terminationSignal = false;
    private final ServerController serverController;
    private final ExecutorService pool = Executors.newCachedThreadPool();


    public ClientHandler(Socket socket, VirtualClientInterface client, ServerController serverController) throws IOException {
        this.socket = socket;
        reader = new ObjectInputStream(socket.getInputStream());
        this.serverController = serverController;
        this.client = client;

        new Thread(this).start();
    }

    /**
     * Continuously listens for incoming client messages and schedules their execution
     * via a thread pool.It deserializes network packets, translates them into server commands,
     * and cleans up connections upon reading errors.
     */
    @Override
    public void run() {
        CommandFactoryServer commandFactoryServer = new CommandFactoryServer();
        try{
            while(!terminationSignal){

                MessageFromClient msg =(MessageFromClient) reader.readObject();
                System.out.println(msg.getHeader());
                ServerCommand cmd = commandFactoryServer.getCommand(msg);

                pool.submit(()->{cmd.execute(client, serverController);});
            }
        } catch (IOException e) {
            Server.closeConnection(client);
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Class Not Found: " + e.getMessage());
        }
    }

    @Override
    public void close() throws Exception {
        if(terminationSignal) return;

        cleanup();
    }

    private void cleanup() {
        try {
            if (reader != null) reader.close();

            if (socket != null && !socket.isClosed()) socket.close();

            if(!pool.isShutdown()) pool.shutdown();

        } catch (IOException e) {
            System.err.println("Error while cleaning up client handler: " + e.getMessage());
        }
    }
}
