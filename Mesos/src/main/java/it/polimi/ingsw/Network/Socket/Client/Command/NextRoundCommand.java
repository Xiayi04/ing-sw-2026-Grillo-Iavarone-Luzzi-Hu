package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Model.Game.Board;
import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.Socket.Server.Command.NextRoundData;

import java.net.Socket;

public class NextRoundCommand implements ClientCommand {
    private final Board board;
    private final int round;
    public NextRoundCommand(NextRoundData data) {
        this.board = data.board();
        this.round = data.round();
    }

    @Override
    public void execute(Socket socket, ClientController clientController) {
        clientController.showUpdateTurn(board, round);
    }
}
