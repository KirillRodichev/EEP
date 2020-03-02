package Controllers;

import Constants.Columns;
import Constants.Parameters;
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

        String selectPassStatement =
                "SELECT * FROM SYSTEM.USERS WHERE USER_EMAIL = '" + req.getParameter("email") + "'";

        Statement statement = null;
        User user = null;
        try {
            Connection connection = getOracleConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectPassStatement);

            while (rs.next()) {
                user = new User(
                        rs.getString(Columns.USER_NAME_COLUMN),
                        rs.getString(Columns.USER_EMAIL_COLUMN),
                        rs.getString(Columns.USER_PASSWORD_COLUMN),
                        rs.getString(Columns.USER_CITY_COLUMN),
                        rs.getString(Columns.USER_GYM_COLUMN)
                );
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (user.getPassword().equals(req.getParameter(Parameters.USER_PASSWORD))) {
            resp.sendRedirect("pages/success.jsp");
        } else {
            resp.sendRedirect("pages/nosuccess.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
