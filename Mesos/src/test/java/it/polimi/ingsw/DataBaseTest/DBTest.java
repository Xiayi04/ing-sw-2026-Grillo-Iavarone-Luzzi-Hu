package it.polimi.ingsw.DataBaseTest;

//import it.polimi.ingsw.Database.DatabaseConnectionManager;
import it.polimi.ingsw.Database.DatabaseManager;
import it.polimi.ingsw.Database.LeaderBoardDAO;
import it.polimi.ingsw.Database.LeaderBoardData;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DBTest {

    @Test
    public void DBTest(){
        try {
            Connection connection = DatabaseManager.getConnection();
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
        }
        System.out.println("CONNECTED");
    }

    @Test
    public void addScoreTest(){
        LeaderBoardDAO dao = new LeaderBoardDAO();

        try {
            dao.addNewPlayerScore("giuseppe", 100, 4);
            dao.addNewPlayerScore("mattia",110,4);
            dao.addNewPlayerScore("denise", 150,3);
            dao.addNewPlayerScore("xiayi", 90,2);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
        }

    }

    @Test
    public void checkLeaderBoardPositionTest() throws SQLException {
        LeaderBoardDAO dao = new LeaderBoardDAO();

        try (Connection conn = DatabaseManager.getConnection();
             java.sql.Statement stmt = conn.createStatement()) {
            stmt.execute("TRUNCATE TABLE gamesDB");
        }

        try {
            dao.addNewPlayerScore("giuseppe", 100, 4);
            dao.addNewPlayerScore("mattia",110,4);
            dao.addNewPlayerScore("denise", 150,3);
            dao.addNewPlayerScore("xiayi", 90,2);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
        }

        assertEquals(2,dao.getPositionInLeaderBoard(4,105));
    }

    @Test
    public void leaderBoardTest() throws SQLException {
        LeaderBoardDAO dao = new LeaderBoardDAO();

        try (Connection conn = DatabaseManager.getConnection();
             java.sql.Statement stmt = conn.createStatement()) {
            stmt.execute("TRUNCATE TABLE gamesDB");
        }

        try {
            dao.addNewPlayerScore("giuseppe", 100, 4);
            dao.addNewPlayerScore("mattia",110,4);
            dao.addNewPlayerScore("denise", 150,4);
            dao.addNewPlayerScore("xiayi", 90,4);
            dao.addNewPlayerScore("aldo", 90,4);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
        }

        List<LeaderBoardData> leaderBoard= new ArrayList<>();
        leaderBoard = dao.getAllTimeLeaderBoard(4);

        for (LeaderBoardData player : leaderBoard) {
            System.out.println("#"+player.position() +" " +player.username() +" |score: "+player.score() + " |on date: " + player.date());
        }
    }

}
