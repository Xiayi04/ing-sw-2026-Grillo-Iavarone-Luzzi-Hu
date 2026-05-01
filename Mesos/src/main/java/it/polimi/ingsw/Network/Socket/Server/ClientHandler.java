package it.polimi.ingsw.Network.Socket.Server;
import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Network.Socket.Server.Command.ServerCommand;
import it.polimi.ingsw.Network.Socket.Server.Command.CommandFactoryServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    static BufferedReader reader;
    GameManager gameManager;
    ClientSocketProxy client;
    public Boolean terminationSignal = false;


    public ClientHandler(Socket socket, GameManager gameManager, ClientSocketProxy client) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {

        try{
            while(true){

                String line = reader.readLine();

                ServerCommand cmd = CommandFactoryServer.getCommand(line);

                cmd.execute(gameManager, client);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
