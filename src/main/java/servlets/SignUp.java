package servlets;

import constants.*;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import static oracleConnection.OracleConnection.getOracleConnection;
import static utils.Utils.*;

@WebServlet("/signUp")
public class SignUp extends HttpServlet {

    private void insertUserData(Connection con, HttpServletRequest req, String sql) throws SQLException {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(Columns.FIRST, req.getParameter(Parameters.USER_NAME));
        ps.setString(Columns.SECOND, req.getParameter(Parameters.USER_EMAIL));
        ps.setString(Columns.THIRD, req.getParameter(Parameters.USER_PASSWORD));
        ps.setString(Columns.FOURTH, req.getParameter(Parameters.USER_MODE));
        ps.execute();
    }

    private int getUserID(Connection con, String sql, String email) throws SQLException {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(Columns.USER_ID, email);
        ResultSet rs = ps.executeQuery();
        int userID = DB.EMPTY_FIELD;
        while (rs.next()) {
            userID = rs.getInt(Columns.USER_ID);
        }
        return userID;
    }

    private User loadUser(HttpServletRequest req, Connection con, String sqlSelect, String sqlInsert)
            throws SQLException, RuntimeException {
        User user = null;
        if (getUserID(con, sqlSelect, req.getParameter(Parameters.USER_EMAIL)) == DB.EMPTY_FIELD) {
            insertUserData(con, req, sqlInsert);
            user = new User(
                    getUserID(con, sqlSelect, req.getParameter(Parameters.USER_EMAIL)),
                    req.getParameter(Parameters.USER_NAME),
                    req.getParameter(Parameters.USER_EMAIL),
                    req.getParameter(Parameters.USER_PASSWORD),
                    req.getParameter(Parameters.USER_MODE)
            );
        } else {
            throw new RuntimeException(ErrorMsg.USER_ALREADY_EXISTS);
        }
        return user;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String insertUserData =
                "INSERT INTO USERS " +
                        "(USER_ID, USER_NAME, USER_EMAIL, USER_PASSWORD, USER_MODE) " +
                        "VALUES " +
                        "(USERS_SEQ.nextval, ?, ?, ?, ?)";

        String selectUserId = "SELECT USER_ID FROM USERS WHERE USER_EMAIL = ?";

        String selectGyms = "SELECT GYM_NAME FROM GYMS";

        String selectCities = "SELECT CITY_NAME FROM CITIES";

        User user = null;
        ArrayList<String> gyms = new ArrayList<>();
        ArrayList<String> cities = new ArrayList<>();
        RequestDispatcher rd;
        try (Connection connection = getOracleConnection()) {

            user = loadUser(req, connection, selectUserId, insertUserData);

            loadList(connection, selectGyms, gyms);
            loadList(connection, selectCities, cities);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            req.setAttribute(ErrorMsg.ERROR_MESSAGE, ErrorMsg.USER_ALREADY_EXISTS);
            rd = req.getRequestDispatcher("pages/error.jsp");
            rd.forward(req, resp);
        }

        forwardToCabinet(req, resp, user, cities, gyms);
    }
}
