package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.Socket.Server.SocketVirtualClient;

import java.net.Socket;

public class StartGameCommand implements ClientCommand {
    SocketVirtualClient.GameStartData gameStartData;
    public StartGameCommand(SocketVirtualClient.GameStartData gameStartData) {
        this.gameStartData = gameStartData;
    }

    @Override
    public void execute(Socket socket, ClientController clientController) {
        clientController.showGameStarted(gameStartData.players(),gameStartData.board());
    }
}
