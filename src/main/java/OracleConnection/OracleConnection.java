package OracleConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class OracleConnection {

    public static Connection getOracleConnection() throws ClassNotFoundException {

        final String USER_NAME = "SYSTEM";
        final String PASSWORD = "orcl2020_!";


        Class.forName("oracle.jdbc.driver.OracleDriver");

        String URL = "jdbc:oracle:thin:@localhost:1521:ORCL";
        Properties info = new Properties();
        info.put( "user", USER_NAME );
        info.put( "password", PASSWORD );

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, info);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }
}
