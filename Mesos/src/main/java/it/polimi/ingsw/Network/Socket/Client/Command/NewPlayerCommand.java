package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.ClientController;

import java.io.IOException;
import java.net.Socket;

public class NewPlayerCommand  implements ClientCommand {
    private final String[] payload;

    public NewPlayerCommand(String[] payload) {
        this.payload = payload;
    }

    public void execute(Socket socket, ClientController clientController) throws IOException {
        if(payload.length != 2) {
            throw new IOException("Invalid payload");
        }

        String playerName = payload[0];
        String totem = payload[1];
        //clientController.addPlayer(new Player(playerName, Totem.valueOf(totem), 0 , null) );
    }
}
