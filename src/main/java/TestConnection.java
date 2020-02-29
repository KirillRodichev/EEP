import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static OracleConnection.OracleConnection.getOracleConnection;

public class TestConnection {

    public static void main(String[] args) {

        String selectTableSQL = "SELECT * from SYSTEM.EMPLOYEES";

        Statement statement = null;

        try {
            Connection connection = getOracleConnection();

            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(selectTableSQL);

            while (rs.next()) {
                System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
