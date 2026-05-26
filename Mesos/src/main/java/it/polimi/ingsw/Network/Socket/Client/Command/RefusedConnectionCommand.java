package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Network.ClientController;

import java.net.Socket;

public class RefusedConnectionCommand implements ClientCommand{

    public RefusedConnectionCommand(){
    }

    @Override
    public void execute(Socket socket, ClientController clientController) {
        //clientController.refuseConnection();
    }
}
