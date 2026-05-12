package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.ClientMain;

import java.io.IOException;
import java.net.Socket;

public class ClientLoginCommand implements ClientCommand{
    private  final String username = ClientMain.getUsername();
    private  final String[] totems;
    private String chosenOne;

    public ClientLoginCommand(String[] totems) {
        this.totems = totems;
    }

    /**
     * @param socket           :
     * @param clientController
     * @throws IOException:
     */
    @Override
    public void execute(Socket socket, ClientController clientController) throws IOException {


    }
}
