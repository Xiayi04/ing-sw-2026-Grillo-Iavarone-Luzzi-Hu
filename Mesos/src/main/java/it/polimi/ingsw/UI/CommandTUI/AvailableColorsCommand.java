package it.polimi.ingsw.UI.CommandTUI;

import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.ClientController;

import java.util.ArrayList;

public class AvailableColorsCommand implements CommandTUI {
    ArrayList<Totem> availableTotems;
    public AvailableColorsCommand(ArrayList<Totem> availableTotems){
        this.availableTotems=availableTotems;
    }

    @Override
    public void execute(ClientController clientController) {
        clientController.showAvailableTotems(availableTotems);
    }
}
