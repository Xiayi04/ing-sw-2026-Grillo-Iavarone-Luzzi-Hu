package it.polimi.ingsw.Database;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class LeaderBoardDAO {
//in questa classe devo creare 3 metodi:
//1:salvataggio della nuova partita del giocatore
//2:ottenere la posizione in classifica che ha ottenuto il giocatore con questa partita
//3:ottenere la classifica totale
//NB:classifica riferita alle partite con stesso numero di giocatori di quella appena conclusa

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

    public int getPositionInLeaderBoard( int numPlayers, int playerScore) throws SQLException{
        String sql= "SELECT COUNT(*)+1 AS position " +
                    "FROM gamesDB WHERE num_players= ? AND score>?";

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
                while(resultSet.next()){
                    String username = resultSet.getString("username");
                    int score = resultSet.getInt("score");
                    LocalDate gameDate = resultSet.getDate("game_date").toLocalDate();
                    String date = gameDate.format(formatter);
                    int num_players = resultSet.getInt("num_players");
                    leaderBoard.addLast(new LeaderBoardData(position,username,score,date));
                    position++;
                }
            }
        }
        return leaderBoard;
    }

}
