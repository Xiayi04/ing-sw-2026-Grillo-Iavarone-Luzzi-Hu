package it.polimi.ingsw.Network.Socket.Server.Command;

import it.polimi.ingsw.Controller.ServerController;
import it.polimi.ingsw.Network.Server;
import it.polimi.ingsw.Network.VirtualClientInterface;

public class QuitCommand implements ServerCommand{
    @Override
    public void execute(VirtualClientInterface client, ServerController serverController) {
        serverController.handleDisconnection(client);
    }
}
