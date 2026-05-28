package it.polimi.ingsw.Network.Socket.Server.Command;

import it.polimi.ingsw.Database.LeaderBoardData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public record DBData(int position, List<LeaderBoardData> leaderboard) implements Serializable {
}
