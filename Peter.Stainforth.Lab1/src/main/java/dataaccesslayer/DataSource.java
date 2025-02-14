package dataaccesslayer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {
    private static Connection connection = null;

    private DataSource() {}

    public static Connection getConnection() {
        String[] connectionInfo = openPropsFile();

        try {
            if (connection == null) {
                connection = DriverManager.getConnection(connectionInfo[0], connectionInfo[1], connectionInfo[2]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

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
