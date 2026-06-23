package it.polimi.ingsw.Database;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class LeaderBoardDAO {
    /**
     * Inserts a new player match record with their score,
     * the current date, and the total player count into the database.
     */
    public void addNewPlayerScore(String username, int playerScore, int numPlayers) throws SQLException{
        String sql =
                "INSERT INTO gamesDB (username, score,game_date, num_players) VALUES (?, ?, ?, ?)";

        try(Connection connection = DatabaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);){

            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, playerScore);
            preparedStatement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            preparedStatement.setInt(4, numPlayers);

            preparedStatement.executeUpdate();
        }
    }

    /**
     * Calculates a player's rank in the leaderboard based on
     * their score and the matching game lobby size.
     */
    public int getPositionInLeaderBoard( int numPlayers, int playerScore) throws SQLException{
        String sql = "SELECT COUNT(DISTINCT score) + 1 AS position " +
                "FROM gamesDB WHERE num_players = ? AND score > ?";

        try(Connection connection = DatabaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);){

            preparedStatement.setInt(1, numPlayers);
            preparedStatement.setInt(2, playerScore);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return resultSet.getInt("position");
                }
            }
            return -1;
        }
    }


    /**
     * Retrieves the complete leaderboard filtered by lobby size,
     * sorted in descending order by score.It dynamically assigns ranking positions,
     * taking ties into account, and formats dates into a standard string representation.
     */
    public List<LeaderBoardData> getAllTimeLeaderBoard(int numPlayers) throws SQLException{
        String sql ="SELECT username, score, game_date, num_players FROM gamesDB "+
                    "WHERE  num_players= ? "+
                    "ORDER BY score DESC";

        List<LeaderBoardData> leaderBoard= new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try(Connection connection = DatabaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);){

            preparedStatement.setInt(1, numPlayers);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                int position = 1;
                for(int i = 0; resultSet.next(); i++){
                    String username = resultSet.getString("username");
                    int score = resultSet.getInt("score");
                    LocalDate gameDate = resultSet.getDate("game_date").toLocalDate();
                    String date = gameDate.format(formatter);
                    int num_players = resultSet.getInt("num_players");
                    if(!leaderBoard.isEmpty() && leaderBoard.get(i-1).score() != score){
                        position++;
                    }
                    leaderBoard.addLast(new LeaderBoardData(position,username,score,date));

                }
            }
        }
        return leaderBoard;
    }

}
