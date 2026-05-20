package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Network.ClientController;

import java.io.IOException;
import java.net.Socket;

public class NewPlayerCommand  implements ClientCommand {
    private final Player payload;

    public NewPlayerCommand(Player payload) {
        this.payload = payload;
    }

    public void execute(Socket socket, ClientController clientController) throws IOException {
        clientController.addPlayerToLocalBoard(payload);
    }
}
