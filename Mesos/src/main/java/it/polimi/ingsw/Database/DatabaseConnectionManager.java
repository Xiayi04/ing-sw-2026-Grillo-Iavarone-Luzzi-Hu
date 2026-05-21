package it.polimi.ingsw.Database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionManager {
    private final static Properties properties = new Properties();

    static {
        try(InputStream input = DatabaseConnectionManager.class
                            .getClassLoader().getResourceAsStream("database.properties")) {

            properties.load(input);
        } catch(IOException e) {
            throw new RuntimeException("Cannot load database configuration", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
        properties.getProperty("db.url"),
        properties.getProperty("db.user"),
        properties.getProperty("db.password"));
    }


}
