package it.polimi.ingsw.UI.CommandTUI;

import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.UI.TUI;

public class MoveTotemCommand implements CommandTUI {
    String provvIndex;
    public MoveTotemCommand(String provvIndex) {
        this.provvIndex = provvIndex;
    }

    @Override
    public void execute(ClientController clientController, TUI tui) {
        try {
            clientController.requestLocalMoveTotem(Integer.parseInt(provvIndex)-1);
        } catch (NumberFormatException e) {
            clientController.showError("Invalid index, please try again.");
        }
    }
}
