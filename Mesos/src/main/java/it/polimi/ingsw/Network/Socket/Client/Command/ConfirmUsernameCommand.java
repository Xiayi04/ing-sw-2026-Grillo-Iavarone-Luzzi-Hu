package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Network.ClientController;

import java.io.IOException;
import java.net.Socket;

public class ConfirmUsernameCommand implements ClientCommand{
    private String username;
    public ConfirmUsernameCommand(String username) {
        this.username = username;
    }
    @Override
    public void execute(Socket socket, ClientController clientController) throws IOException {
        clientController.showConfirmUsername(username);
    }
}
