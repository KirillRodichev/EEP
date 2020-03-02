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

    private static final int USER_NAME_COLUMN = 2;
    private static final int USER_EMAIL_COLUMN = 3;
    private static final int USER_PASSWORD_COLUMN = 4;
    private static final int USER_CITY_COLUMN = 5;
    private static final int USER_GYM_COLUMN = 6;

    private static final String PASSWORD_PARAM = "password";

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
                        rs.getString(USER_NAME_COLUMN),
                        rs.getString(USER_EMAIL_COLUMN),
                        rs.getString(USER_PASSWORD_COLUMN),
                        rs.getString(USER_CITY_COLUMN),
                        rs.getString(USER_GYM_COLUMN)
                );
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (user.getPassword().equals(req.getParameter(PASSWORD_PARAM))) {
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
