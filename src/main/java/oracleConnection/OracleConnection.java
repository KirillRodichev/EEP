package oracleConnection;

import constants.Columns;

import java.sql.*;
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

    public static ResultSet getSingleIntResultSet(String sql, Connection con, int val) throws SQLException {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(Columns.GYM_ID, val);
        return ps.executeQuery();
    }
}
