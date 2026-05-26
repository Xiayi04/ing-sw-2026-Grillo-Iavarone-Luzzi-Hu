package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Network.ClientController;

import java.net.Socket;

public class PlayerTurnCommand implements ClientCommand {
    private final String username;
    public PlayerTurnCommand(String username) {
        this.username = username;
    }

    @Override
    public void execute(Socket socket, ClientController clientController) {
        clientController.showPlayerTurn(username);
    }
}
