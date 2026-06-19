package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.Socket.Server.Command.Pick;

import java.net.Socket;

public class PickedCardCommand implements ClientCommand {
    Pick p;
    public PickedCardCommand(Pick p) {
        this.p = p;
    }

    @Override
    public void execute(Socket socket, ClientController clientController) {
        clientController.showPickedCard(p.username(), p.isUpper(),p.isBuilding(), p.index(), p.round());
    }
}
