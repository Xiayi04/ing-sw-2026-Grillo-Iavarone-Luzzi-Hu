package it.polimi.ingsw.Network.Socket.Server;
import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Controller.ServerController;
import it.polimi.ingsw.Network.Server;
import it.polimi.ingsw.Network.Socket.Server.Command.ServerCommand;
import it.polimi.ingsw.Network.Socket.Server.Command.CommandFactoryServer;
import it.polimi.ingsw.Network.VirtualClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    static BufferedReader reader;
    GameManager gameManager;
    VirtualClient client;
    public Boolean terminationSignal = false;
    private final ServerController serverController;


    public ClientHandler(Socket socket, GameManager gameManager, VirtualClient client, ServerController serverController) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.serverController = serverController;
    }

    @Override
    public void run() {

        try{
            while(true){

                String line = reader.readLine();

                ServerCommand cmd = CommandFactoryServer.getCommand(line);

                cmd.execute(gameManager, client, serverController);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
