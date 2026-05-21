package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.ClientController;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ShowAvailableColorsCommand implements ClientCommand{
    ArrayList<Totem> totems;
    public ShowAvailableColorsCommand(ArrayList<Totem> totems){
        this.totems = totems;
    }

    @Override
    public void execute(Socket socket, ClientController clientController) throws IOException {
       clientController.showAvailableTotems(totems);
    }
}
