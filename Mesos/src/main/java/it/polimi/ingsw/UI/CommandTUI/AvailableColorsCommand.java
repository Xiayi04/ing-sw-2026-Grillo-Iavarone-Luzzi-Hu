package it.polimi.ingsw.UI.CommandTUI;

import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.UI.TUI;

public class AvailableColorsCommand implements CommandTUI {

    public AvailableColorsCommand(){

    }

    @Override
    public void execute(ClientController clientController, TUI tui) {
        clientController.requestLocalAvailableTotems();
    }
}
