package it.polimi.ingsw.Network.Socket.Server;
import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Controller.ServerController;
import it.polimi.ingsw.Network.Socket.Server.Command.ServerCommand;
import it.polimi.ingsw.Network.Socket.Server.Command.CommandFactoryServer;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final BufferedReader reader;
    GameManager gameManager;
    VirtualClientInterface client;
    public Boolean terminationSignal = false;
    private final ServerController serverController;


    public ClientHandler(Socket socket, GameManager gameManager, VirtualClientInterface client, ServerController serverController) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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

                String line = reader.readLine();
                System.out.println(line);
                ServerCommand cmd = commandFactoryServer.getCommand(line);

                cmd.execute(gameManager, client, serverController);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
