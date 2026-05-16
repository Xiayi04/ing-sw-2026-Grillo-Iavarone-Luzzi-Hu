package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Network.ClientController;

import java.io.IOException;
import java.net.Socket;

public class NotFoundCommand implements ClientCommand{
    private  final String command;
    public NotFoundCommand(String command) {
        this.command = command;
    }

    @Override
    public void execute(Socket socket, ClientController clientController) throws IOException {

    }
}
