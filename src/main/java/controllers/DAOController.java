package controllers;

import connection.OracleConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public abstract class DAOController<E> {
    private Connection connection;

    public DAOController() {
        try {
            connection = OracleConnectionPool.getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract List<E> getAll() throws SQLException;
    public abstract E update(E entity);
    public abstract E getById(int id) throws SQLException;
    public abstract void delete(int id);
    public abstract void create(E entity) throws SQLException;

    public void releaseConnection() {
        OracleConnectionPool.releaseConnection(connection);
    }

    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    public void closePreparedStatement(PreparedStatement ps) throws SQLException {
        if (ps != null) {
            ps.close();
        }
    }
}
