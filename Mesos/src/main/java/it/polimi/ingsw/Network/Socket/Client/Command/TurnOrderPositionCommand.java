package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.Socket.Server.Command.TotemPosition;
import it.polimi.ingsw.Network.Socket.Server.SocketVirtualClient;

import java.net.Socket;

public class TurnOrderPositionCommand implements ClientCommand {
    TotemPosition totemPosition;
    public TurnOrderPositionCommand(TotemPosition totemPosition) {
        this.totemPosition = totemPosition;
    }

    @Override
    public void execute(Socket socket, ClientController clientController) {
        clientController.showLocalReturnToTOC(totemPosition.playerName(),totemPosition.index());
    }
}
