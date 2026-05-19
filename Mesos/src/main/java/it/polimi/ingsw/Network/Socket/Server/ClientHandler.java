package it.polimi.ingsw.Network.Socket.Server;
import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Controller.ServerController;
import it.polimi.ingsw.Network.Socket.Client.MessageFromClient;
import it.polimi.ingsw.Network.Socket.Server.Command.ServerCommand;
import it.polimi.ingsw.Network.Socket.Server.Command.CommandFactoryServer;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final ObjectInputStream reader;
    GameManager gameManager;
    VirtualClientInterface client;
    public Boolean terminationSignal = false;
    private final ServerController serverController;


    public ClientHandler(Socket socket, GameManager gameManager, VirtualClientInterface client, ServerController serverController) throws IOException {
        this.socket = socket;
        reader = new ObjectInputStream(socket.getInputStream());
        this.serverController = serverController;
        new Thread(this).start();
        this.client = client;
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        CommandFactoryServer commandFactoryServer = new CommandFactoryServer();
        try{
            while(true){

                MessageFromClient msg =(MessageFromClient) reader.readObject();
                System.out.println(msg.getHeader());
                ServerCommand cmd = commandFactoryServer.getCommand(msg);

                cmd.execute(client, serverController);
            }
        } catch (EOFException | SocketException e){
            serverController.handleDisconnection(client);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
