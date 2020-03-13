package servlets;

import constants.Columns;
import constants.DB;
import constants.Parameters;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

import static oracleConnection.OracleConnection.getOracleConnection;

@WebServlet("/signUp")
public class SignUp extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = new User(
                /*
                ============CHANGE ID PARAM============
                 */
                Integer.parseInt(req.getParameter(Parameters.USER_MODE)),


                req.getParameter(Parameters.USER_NAME),
                req.getParameter(Parameters.USER_EMAIL),
                req.getParameter(Parameters.USER_PASSWORD),
                req.getParameter(Parameters.USER_MODE)
        );

        String insertStatement =
                "INSERT INTO USERS " +
                "(USER_ID, USER_NAME, USER_EMAIL, USER_PASSWORD, USER_MODE) " +
                "VALUES " +
                "(USERS_SEQ.nextval, ?, ?, ?, ?)";

        try {
            Connection connection = getOracleConnection();

            PreparedStatement ps = connection.prepareStatement(insertStatement);
            ps.setString(Columns.USER_NAME, user.getName());
            ps.setString(Columns.USER_EMAIL, user.getEmail());
            ps.setString(Columns.USER_PASSWORD, user.getPassword());
            ps.setString(Columns.USER_MODE, user.getMode());
            ps.execute();

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
