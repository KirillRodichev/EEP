package controllers;

import constants.Columns;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserController extends DAOController<User> {

    private static final String SELECT_ALL = "SELECT * FROM USERS";
    private static final String SELECT_BY_ID = "SELECT * FROM USERS WHERE USER_ID = ?";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM USERS WHERE USER_EMAIL = ?";
    private static final String INSERT =
            "INSERT INTO USERS " +
                    "(USER_ID, USER_NAME, USER_EMAIL, USER_PASSWORD, USER_MODE) " +
                    "VALUES " +
                    "(USERS_SEQ.nextval, ?, ?, ?, ?)";

    public User getByEmail(String email) throws SQLException {
        User user = null;
        PreparedStatement ps = getPreparedStatement(SELECT_BY_EMAIL);
        ps.setString(Columns.FIRST, email);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            user = new User(
                    rs.getInt(Columns.USER_ID),
                    rs.getString(Columns.USER_NAME),
                    rs.getString(Columns.USER_EMAIL),
                    rs.getString(Columns.USER_PASSWORD),
                    rs.getString(Columns.USER_MODE)
            );
        }
        return user;
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        PreparedStatement ps = getPreparedStatement(SELECT_ALL);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            users.add(new User(
                    rs.getInt(Columns.USER_ID),
                    rs.getString(Columns.USER_NAME),
                    rs.getString(Columns.USER_EMAIL),
                    rs.getString(Columns.USER_PASSWORD),
                    rs.getString(Columns.USER_MODE)
            ));
        }
        closePreparedStatement(ps);
        return users;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User getById(int id) throws SQLException {
        User user = null;
        PreparedStatement ps = getPreparedStatement(SELECT_BY_ID);
        ps.setInt(Columns.FIRST, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            user = new User(
                    rs.getInt(Columns.USER_ID),
                    rs.getString(Columns.USER_NAME),
                    rs.getString(Columns.USER_EMAIL),
                    rs.getString(Columns.USER_PASSWORD),
                    rs.getString(Columns.USER_MODE)
            );
        }
        return user;
    }

    @Override
    public void delete(int id) {
    }

    @Override
    public void create(User user) throws SQLException {
        PreparedStatement ps = getPreparedStatement(INSERT);
        ps.setString(Columns.FIRST, user.getName());
        ps.setString(Columns.SECOND, user.getEmail());
        ps.setString(Columns.THIRD, user.getPassword());
        ps.setString(Columns.FOURTH, user.getMode());
        ps.execute();
    }
}