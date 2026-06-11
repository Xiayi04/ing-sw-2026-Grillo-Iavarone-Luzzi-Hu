package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.PlayerScore;
import it.polimi.ingsw.Network.Socket.Server.Command.EndGameData;

import java.net.Socket;
import java.util.List;

public class EndGameCommand implements ClientCommand{
    String winner;
    List<PlayerScore> leaderboard;
    public EndGameCommand(EndGameData endGameData) {
        this.winner = endGameData.winner();
        this.leaderboard = endGameData.leaderboard();
    }
    @Override
    public void execute(Socket socket, ClientController clientController) {
        clientController.handleEndGameNormally(winner, leaderboard);
    }
}
