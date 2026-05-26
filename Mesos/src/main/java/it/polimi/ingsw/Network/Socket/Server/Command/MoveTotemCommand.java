package it.polimi.ingsw.Network.Socket.Server.Command;

import it.polimi.ingsw.Controller.ServerController;
import it.polimi.ingsw.Network.Socket.Server.SocketVirtualClient;
import it.polimi.ingsw.Network.VirtualClientInterface;

public class MoveTotemCommand implements ServerCommand {
    private TotemPosition totemPosition;
    public MoveTotemCommand(TotemPosition position){
        totemPosition = position;
    }
    @Override
    public void execute(VirtualClientInterface client, ServerController serverController) {
        serverController.moveTotemRequest(totemPosition.playerName(), totemPosition.index());
    }
}
