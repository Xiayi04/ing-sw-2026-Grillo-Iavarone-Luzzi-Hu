package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Database.LeaderBoardData;
import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.Socket.Server.Command.DBData;

import java.net.Socket;
import java.util.List;

public class DBLeaderBoardCommand implements ClientCommand{
    int PlayerPositionInDB;
    List<LeaderBoardData> leaderboard;

    public DBLeaderBoardCommand(DBData data){
        this.PlayerPositionInDB = data.position();
        this.leaderboard = data.leaderboard();
    }

    @Override
    public void execute(Socket socket, ClientController clientController) {
        clientController.updateLeaderboardFromDB(PlayerPositionInDB, leaderboard);
    }
}
