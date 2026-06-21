package it.polimi.ingsw.UI.CommandTUI;

import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.UI.TUI;

public class ConnectionSelectionCommand implements CommandTUI {
    private final int choice;

    public ConnectionSelectionCommand(int choice) {
        this.choice = choice;
    }

    @Override
    public void execute(ClientController clientController, TUI tui) {
        clientController.setServerConnection(choice == 0);
    }
}
