package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Network.ClientController;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class ShowMSGCommand implements ClientCommand{
    private String message;
    public ShowMSGCommand(String[] message){
        this.message = Arrays.toString(message);
    }


    @Override
    public void execute(Socket socket, ClientController clientController) throws IOException {
        //clientController.showMessage(message);
    }
}
