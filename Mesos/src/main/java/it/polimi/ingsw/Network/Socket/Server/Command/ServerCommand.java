package it.polimi.ingsw.Network.Socket.Server.Command;

import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Controller.ServerController;
import it.polimi.ingsw.Network.Socket.Server.ClientSocketProxy;
import it.polimi.ingsw.Network.VirtualClient;

public interface ServerCommand {
    void execute(GameManager gameManager, VirtualClient client, ServerController serverController);
}
