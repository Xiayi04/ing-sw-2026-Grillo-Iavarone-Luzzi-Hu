package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Model.Game.Totem;
import it.polimi.ingsw.Network.ClientController;

import java.net.Socket;
import java.util.ArrayList;

public class ShowAvailableColorsCommand implements ClientCommand{
    ArrayList<Totem> totems;
    public ShowAvailableColorsCommand(ArrayList<Totem> totems){
        this.totems = totems;
    }

    @Override
    public void execute(Socket socket, ClientController clientController) {
       clientController.showAvailableTotems(totems);
    }
}
