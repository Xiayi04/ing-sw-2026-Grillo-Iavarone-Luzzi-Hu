package it.polimi.ingsw.Network.Socket.Server.Command;

import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Controller.ServerController;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.rmi.RemoteException;

public class LoginCommand implements ServerCommand {
    private final String username;
    private final String totem;



    public LoginCommand(String username, String totem) {
        this.username = username;
        this.totem = totem;
    }

    @Override
    public void execute(GameManager gameManager, VirtualClientInterface client, ServerController serverController) {
        serverController.addPlayerToGame(username,totem,client);
    }
}
