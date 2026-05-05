package it.polimi.ingsw.Network.Socket.Client;

import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.Socket.Client.Command.ClientCommand;
import it.polimi.ingsw.Network.Socket.Client.Command.CommandFactoryClientSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketClient implements Runnable{
    //private Socket socket;
    public static final Object inputLock = new Object();
    public static final Object outputLock = new Object();
    public final ClientController clientController;
    public SocketClient(Socket socket, ClientController clientController) {
        this.clientController = clientController;
        run();
    }

    @Override
    public void run(){
        try {
            Socket socket = new Socket("localhost", 777);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            //implementazione heartbeat
            CommandFactoryClientSide commandFactory = new CommandFactoryClientSide();
            while(true){
               String line = reader.readLine();
               ClientCommand cmd = commandFactory.getCommand(line);
               cmd.execute(socket, );
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
