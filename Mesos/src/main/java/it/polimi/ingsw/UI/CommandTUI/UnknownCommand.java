package it.polimi.ingsw.UI.CommandTUI;

import it.polimi.ingsw.Network.ClientController;

public class UnknownCommand implements CommandTUI {
    @Override
    public void execute(ClientController clientController) {
        new Thread(() -> {
            clientController.unknownCommandError();
        }).start();
    }
}
