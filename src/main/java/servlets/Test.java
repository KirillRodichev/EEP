package servlets;

import constants.Columns;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static oracleConnection.OracleConnection.getOracleConnection;

/*@WebServlet(
        urlPatterns = "/test",
        initParams =
                {
                        @WebInitParam(name = "allowedTypes", value = "png, css, js")
                }
)*/
@WebServlet("/test")
public class Test extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String selectPassStatement =
                "SELECT * FROM SYSTEM.USERS_1 WHERE USER_EMAIL = '" + req.getParameter("email") + "'";

        Statement statement;
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

        req.setAttribute("user", user);
        RequestDispatcher rd = req.getRequestDispatcher("pages/cabinet.jsp");
        rd.forward(req, resp);
    }
}
