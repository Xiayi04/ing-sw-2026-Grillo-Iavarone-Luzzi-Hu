package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.ClientController;

import java.io.IOException;
import java.net.Socket;

public class ConfirmTotemCommand implements ClientCommand{
    private final Totem totem;
    public ConfirmTotemCommand(Totem totem) {
        this.totem = totem;
    }

    @Override
    public void execute(Socket socket, ClientController clientController) throws IOException {
        clientController.confirmTotem(totem);
    }
}
