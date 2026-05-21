package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Network.ClientController;

import java.io.IOException;
import java.net.Socket;

public class UpdateEraCommand implements ClientCommand {
    int era;
    public UpdateEraCommand(int era) {
        this.era = era;
    }

    @Override
    public void execute(Socket socket, ClientController clientController) throws IOException {
        clientController.showLocalUpdateEra();
    }
}
