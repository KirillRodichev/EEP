package Controllers;

import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.Enumeration;

import static OracleConnection.OracleConnection.getOracleConnection;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String selectPassStatement = "SELECT * FROM SYSTEM.USERS WHERE EMAIL = '" + req.getParameter("email") + "'";

        Statement statement = null;
        User user = null;
        try {
            Connection connection = getOracleConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectPassStatement);

            while (rs.next()) {
                user = new User(
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                );
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (user.getPassword().equals(req.getParameter("password"))) {
            resp.sendRedirect("success.jsp");
        } else {
            resp.sendRedirect("nosuccess.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
