package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Network.ClientController;

import java.io.IOException;
import java.net.Socket;

public class NextTurnCommand implements ClientCommand {
    private final Board board;
    public NextTurnCommand(Board board) {
        this.board = board;
    }

    @Override
    public void execute(Socket socket, ClientController clientController) throws IOException {
        clientController.showUpdateTurn(board);
    }
}
