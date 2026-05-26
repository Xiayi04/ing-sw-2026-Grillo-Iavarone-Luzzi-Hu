package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Network.ClientController;

import java.net.Socket;

public class ConfirmUsernameCommand implements ClientCommand{
    private String username;
    public ConfirmUsernameCommand(String username) {
        this.username = username;
    }
    @Override
    public void execute(Socket socket, ClientController clientController) {
        clientController.showConfirmUsername(username);
    }
}
