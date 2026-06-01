package it.polimi.ingsw.UI.CommandTUI;

import it.polimi.ingsw.Network.ClientController;

public class MoveTotemCommand implements CommandTUI {
    String provvIndex;
    public MoveTotemCommand(String provvIndex) {
        this.provvIndex = provvIndex;
    }

    @Override
    public void execute(ClientController clientController) {
        try {
            clientController.requestLocalMoveTotem(Integer.parseInt(provvIndex));
        } catch (NumberFormatException e) {
            //clientController.showErrorMessage("Please enter a valid index");
        }
    }
}
