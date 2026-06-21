package it.polimi.ingsw.UI.CommandTUI;

import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.UI.TUI;

public class ShowCardsCommand implements CommandTUI{
    String totem;
    public ShowCardsCommand(String totem) {}

    @Override
    public void execute(ClientController clientController, TUI tui) {

    }
}
