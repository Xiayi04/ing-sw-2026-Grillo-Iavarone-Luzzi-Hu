package it.polimi.ingsw.Network.Socket.Server.Command;

import it.polimi.ingsw.Controller.ServerController;
import it.polimi.ingsw.Network.VirtualClientInterface;

public interface ServerCommand {
    void execute( VirtualClientInterface client, ServerController serverController);
}
