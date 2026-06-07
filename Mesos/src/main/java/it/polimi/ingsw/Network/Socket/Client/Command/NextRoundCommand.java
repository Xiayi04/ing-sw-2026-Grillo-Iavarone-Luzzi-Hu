package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Network.ClientController;

import java.net.Socket;

public class NextRoundCommand implements ClientCommand {
    private final Board board;
    public NextRoundCommand(Board board) {
        this.board = board;
    }

    @Override
    public void execute(Socket socket, ClientController clientController) {
        clientController.showUpdateTurn(board);
    }
}
