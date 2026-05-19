package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.Socket.Server.SocketVirtualClient;

import java.io.IOException;
import java.net.Socket;

public class MovedTotemCommand implements ClientCommand {
    SocketVirtualClient.TotemPosition pos;
    public MovedTotemCommand(SocketVirtualClient.TotemPosition pos) {
        this.pos = pos;
    }

    @Override
    public void execute(Socket socket, ClientController clientController) throws IOException {
        if(pos.player()!=null )
            clientController.movedTotem(pos.player(),pos.index());
    }
}
