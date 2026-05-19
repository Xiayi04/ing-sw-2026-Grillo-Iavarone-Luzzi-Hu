package it.polimi.ingsw.Network.Socket.Server.Command;

import it.polimi.ingsw.Controller.ServerController;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.util.Arrays;

public class SetUsernameCommand implements ServerCommand{
    String username;
    public SetUsernameCommand(String username){
        this.username = username;
    }

    @Override
    public void execute(VirtualClientInterface client, ServerController serverController) {
        serverController.getLobby().addUsername(username,client);
    }
}
