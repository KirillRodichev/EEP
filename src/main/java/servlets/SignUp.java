package servlets;

import constants.Columns;
import constants.Parameters;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

import static oracleConnection.OracleConnection.getOracleConnection;

/*@WebServlet(
        urlPatterns = "/signUp",
        initParams =
                {
                        @WebInitParam(name = "allowedTypes", value = "png, css, js")
                }
)*/
@WebServlet("/signUp")
public class SignUp extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = new User(
                req.getParameter(Parameters.USER_NAME),
                req.getParameter(Parameters.USER_EMAIL),
                req.getParameter(Parameters.USER_PASSWORD),
                req.getParameter(Parameters.USER_CITY),
                req.getParameter(Parameters.USER_GYM)
        );

        String insertStatement =
                "INSERT INTO SYSTEM.USERS_1 " +
                "(USER_ID, USER_NAME, USER_EMAIL, USER_PASSWORD, USER_CITY, USER_GYM) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?)";

        try {
            Connection connection = getOracleConnection();

            PreparedStatement ps = connection.prepareStatement(insertStatement);
            ps.setInt(Columns.USER_ID_COLUMN, User.getId());
            ps.setString(Columns.USER_NAME_COLUMN, user.getName());
            ps.setString(Columns.USER_EMAIL_COLUMN, user.getEmail());
            ps.setString(Columns.USER_PASSWORD_COLUMN, user.getPassword());
            ps.setString(Columns.USER_CITY_COLUMN, user.getCity());
            ps.setString(Columns.USER_GYM_COLUMN, user.getGym());

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
