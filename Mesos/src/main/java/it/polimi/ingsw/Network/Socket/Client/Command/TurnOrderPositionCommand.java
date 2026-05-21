package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.Socket.Server.SocketVirtualClient;

import java.io.IOException;
import java.net.Socket;

public class TurnOrderPositionCommand implements ClientCommand {
    SocketVirtualClient.TotemPosition totemPosition;
    public TurnOrderPositionCommand(SocketVirtualClient.TotemPosition totemPosition) {
        this.totemPosition = totemPosition;
    }

    @Override
    public void execute(Socket socket, ClientController clientController) throws IOException {
        clientController.showLocalReturnToTOC(totemPosition.player(),totemPosition.index());
    }
}
