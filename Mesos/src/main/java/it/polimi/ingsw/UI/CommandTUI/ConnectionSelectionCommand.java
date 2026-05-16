package it.polimi.ingsw.UI.CommandTUI;

import it.polimi.ingsw.Network.ClientController;

public class ConnectionSelectionCommand implements CommandTUI {
    private int choice;

    public ConnectionSelectionCommand(int choice) {
        this.choice = choice;
    }

    @Override
    public void execute(ClientController clientController) {
        new Thread(() -> {
            clientController.setConnection(choice);
        }).start();
    }
}
