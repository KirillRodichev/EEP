package connection;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class OracleConnectionPool {
    private static String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static String URL = "jdbc:oracle:thin:@localhost:1521:ORCL";
    private static String USER = "SYSTEM";
    private static String PASSWORD = "orcl2020_!";
    private static int INITIAL_POOL_SIZE = 5;
    private static int MAX_POOL_SIZE = 10;

    private static List<Connection> connectionPool;
    private static List<Connection> usedConnections;

    private static Connection createConnection() throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER);
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public OracleConnectionPool(List<Connection> pool) {
        connectionPool = pool;
        usedConnections = new ArrayList<>(MAX_POOL_SIZE);
    }

    public static OracleConnectionPool create() throws SQLException, ClassNotFoundException {
        List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection());
        }
        return new OracleConnectionPool(pool);
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        System.out.println("GET CONNECTION: POOL SIZE = " + connectionPool.size() + ", USED = " + usedConnections.size());
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < MAX_POOL_SIZE) {
                connectionPool.add(createConnection());
            } else {
                //throw new RuntimeException("Maximum pool size reached, no available connections!");
                System.out.println("CREATE");
                create();
                System.out.println("GET CONNECTION: POOL SIZE = " + connectionPool.size() + ", USED = " + usedConnections.size());
            }
        }
        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    public static void releaseConnection(Connection connection) {
        connectionPool.add(connection);
        usedConnections.remove(connection);
    }

    public static void shutdown() throws SQLException {
        usedConnections.forEach(OracleConnectionPool::releaseConnection);
        for (Connection c : connectionPool) {
            c.close();
        }
        connectionPool.clear();
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }
}
