package oracleConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static oracleConnection.OracleConnection.getOracleConnection;

public class TestConnection {

    public static void main(String[] args) {

        String selectTableSQL =
                "select * from SYSTEM.CITIES " +
                        "full outer join SYSTEM.GYMS ON SYSTEM.CITIES.CITY_NAME = SYSTEM.GYMS.GYM_NAME " +
                        "WHERE SYSTEM.GYMS.USER_ID IS NULL AND SYSTEM.CITIES.USER_ID IS NULL";

        Statement statement;

        try {
            Connection connection = getOracleConnection();

            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(selectTableSQL);

            while (rs.next()) {
                //System.out.println(rs.getString(2)+"  "+rs.getString(2)+"  "+rs.getString(3));
                System.out.println(rs.getString(1) + "   " + rs.getString(3));
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
