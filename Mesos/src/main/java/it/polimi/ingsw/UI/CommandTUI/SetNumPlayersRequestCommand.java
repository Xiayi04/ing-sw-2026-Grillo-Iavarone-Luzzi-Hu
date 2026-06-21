package it.polimi.ingsw.UI.CommandTUI;

import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.UI.TUI;

public class SetNumPlayersRequestCommand implements CommandTUI{
    String numPlayers;
    public SetNumPlayersRequestCommand(String numPlayers){
        this.numPlayers=numPlayers;
    }

    @Override
    public void execute(ClientController clientController, TUI tui) {
        try {
            clientController.setNumPlayers(Integer.parseInt(numPlayers));
        } catch (NumberFormatException e) {
            clientController.showError("Please enter a number between 2 and 5");
        }
    }
}
