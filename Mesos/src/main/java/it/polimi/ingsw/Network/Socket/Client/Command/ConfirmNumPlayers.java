package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Network.ClientController;

import java.io.IOException;
import java.net.Socket;

public class ConfirmNumPlayers implements ClientCommand{
    int num;
    public ConfirmNumPlayers(int num) {
        this.num = num;
    }

    @Override
    public void execute(Socket socket, ClientController clientController) throws IOException {
        clientController.confirmNumPlayers(num);
    }
}
