package it.polimi.ingsw.UI.CommandTUI;

import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.ClientController;

import java.util.ArrayList;

public class AvailableColorsCommand implements CommandTUI {

    public AvailableColorsCommand(){

    }

    @Override
    public void execute(ClientController clientController) {
        clientController.requestLocalAvailableTotems();
    }
}
