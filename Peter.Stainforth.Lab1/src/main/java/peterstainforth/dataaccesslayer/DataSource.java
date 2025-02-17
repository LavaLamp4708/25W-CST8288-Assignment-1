package peterstainforth.dataaccesslayer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Class containing methods to retrieve connection data from database.properties and use it to connect to the database.
 * @author Peter Stainforth
 * @studentNumber 041121409
 */
public class DataSource {
    private static Connection connection = null;

    /**
     * Privated constructor.
     */
    private DataSource() {}

    /**
     * Uses the openPropsFile method to retrieve database connection info and get the connection. 
     * @return connection
     */
    public static Connection getConnection() {
        String[] connectionInfo = openPropsFile();

        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(connectionInfo[0], connectionInfo[1], connectionInfo[2]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Uses the InputStream class to access the database.properties file and the Properties class to load the properties so that they may be put into a String array and returned.
     * @return String[3] info
     */
    private static String[] openPropsFile() {
        Properties props = new Properties();

        try (InputStream in = Files.newInputStream(Paths.get("src/main/java/database.properties"))){
            props.load(in);
        } catch (IOException e){
            e.printStackTrace();
        }
        String[] info = new String[3];
        info[0] = props.getProperty("jdbc.url");
        info[1] = props.getProperty("jdbc.username");
        info[2] = props.getProperty("jdbc.password");
        return info;
    }
}
