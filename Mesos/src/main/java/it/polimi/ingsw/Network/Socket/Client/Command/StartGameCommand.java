package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.Socket.Server.SocketVirtualClient;

import java.io.IOException;
import java.net.Socket;

public class StartGameCommand implements ClientCommand {
    SocketVirtualClient.GameStartData gameStartData;
    public StartGameCommand(SocketVirtualClient.GameStartData gameStartData) {
        this.gameStartData = gameStartData;
    }

    @Override
    public void execute(Socket socket, ClientController clientController) throws IOException {
        //clientController.showStartGame(gameStartData.players(),gameStartData.board());
    }
}
