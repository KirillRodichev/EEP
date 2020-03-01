package Controllers;

import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

import static OracleConnection.OracleConnection.getOracleConnection;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = new User(
                req.getParameter("name"),
                req.getParameter("email"),
                req.getParameter("password"),
                req.getParameter("city"),
                req.getParameter("gym")
        );

        String insertStatement =
                "INSERT INTO SYSTEM.USERS " +
                "(USERID, NAME, EMAIL, PASSWORD, CITY, GYM) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?)";

        try {
            Connection connection = getOracleConnection();

            PreparedStatement ps = connection.prepareStatement(insertStatement);
            ps.setInt(1, User.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getCity());
            ps.setString(6, user.getGym());

            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        resp.sendRedirect("success.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
