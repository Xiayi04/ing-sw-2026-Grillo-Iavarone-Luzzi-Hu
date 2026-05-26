package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.Socket.Server.Command.TotemPosition;
import it.polimi.ingsw.Network.Socket.Server.SocketVirtualClient;

import java.net.Socket;

public class MovedTotemCommand implements ClientCommand {
    TotemPosition pos;
    public MovedTotemCommand(TotemPosition pos) {
        this.pos = pos;
    }

    @Override
    public void execute(Socket socket, ClientController clientController) {
        clientController.showTotemMoved(pos.playerName(), pos.index());
    }
}
