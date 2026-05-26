package it.polimi.ingsw.Database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseManager {
    private final static Properties properties = new Properties();

    static {
        try (InputStream input = DatabaseManager.class
                .getClassLoader().getResourceAsStream("database.properties")) {

            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load database configuration", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                properties.getProperty("db.url"),
                properties.getProperty("db.user"),
                properties.getProperty("db.password"));
    }

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS gamesDB(" +
            "id INT auto_increment primary key," +
            "username VARCHAR(50) NOT NULL," +
            "score INT NOT NULL," +
            "game_date datetime NOT NULL," +
            "num_players INT NOT NULL" +
        ");";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Database table created");
        } catch (SQLException e) {
            System.out.println("Database table creation failed: " + e.getMessage());
        }

    }


}
