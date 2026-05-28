package it.polimi.ingsw.Database;

import java.io.Serializable;

public record LeaderBoardData(int position, String username, int score, String date) implements Serializable {
}
