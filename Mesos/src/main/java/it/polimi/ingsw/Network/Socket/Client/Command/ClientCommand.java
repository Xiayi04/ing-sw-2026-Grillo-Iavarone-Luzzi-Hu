package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Network.ClientController;

import java.io.IOException;
import java.net.Socket;

public interface ClientCommand {
    void execute(Socket socket, ClientController clientController) throws IOException;
}
