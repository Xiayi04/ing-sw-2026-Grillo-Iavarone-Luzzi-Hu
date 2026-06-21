package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Network.ClientController;

import java.net.Socket;

public class SkipCommand implements ClientCommand{
    @Override
    public void execute(Socket socket, ClientController clientController) {
        clientController.requestSkip();
    }
}
