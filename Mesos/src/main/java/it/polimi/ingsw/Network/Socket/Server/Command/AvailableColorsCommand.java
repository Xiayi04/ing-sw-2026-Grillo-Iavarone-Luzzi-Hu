package it.polimi.ingsw.Network.Socket.Server.Command;

import it.polimi.ingsw.Controller.ServerController;
import it.polimi.ingsw.Network.VirtualClientInterface;

public class AvailableColorsCommand implements ServerCommand {

    public AvailableColorsCommand() {}

    @Override
    public void execute(VirtualClientInterface client, ServerController serverController) {
        serverController.getLobby().sendAvailableColors(client);
    }
}
