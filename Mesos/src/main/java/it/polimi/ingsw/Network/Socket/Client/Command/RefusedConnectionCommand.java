package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Network.ClientController;

import java.io.IOException;
import java.net.Socket;

public class RefusedConnectionCommand implements ClientCommand{

    public RefusedConnectionCommand(){
    }

    @Override
    public void execute(Socket socket, ClientController clientController) throws IOException {
        //clientController.refuseConnection();
    }
}
