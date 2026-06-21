package it.polimi.ingsw.UI.CommandTUI;

import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.UI.TUI;

public interface CommandTUI {
    void execute(ClientController clientController, TUI tui);
}
