package it.polimi.ingsw.UI.CommandTUI;

import it.polimi.ingsw.Network.ClientController;

public class SetUsernameCommand implements CommandTUI {
    private final String username;

    public SetUsernameCommand(String username){
        this.username = username;
    }

    @Override
    public void execute(ClientController clientController) {
        clientController.setTmpUsername(username.trim());
    }
}
