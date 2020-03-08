package servlets;

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
import java.util.ArrayList;
import java.util.List;

import static oracleConnection.OracleConnection.getOracleConnection;


/*@WebServlet(
        urlPatterns = "/retriever",
        initParams =
                {
                        @WebInitParam(name = "allowedTypes", value = "png, css, js")
                }
)*/
@WebServlet("/retriever")
public class FormDataRetriever extends HttpServlet {

    private static final int GYM_COLUMN_INDEX = 3;
    private static final int CITY_COLUMN_INDEX = 1;

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String selectQuery =
                "select * from SYSTEM.CITIES " +
                        "full outer join SYSTEM.GYMS ON SYSTEM.CITIES.CITY_NAME = SYSTEM.GYMS.GYM_NAME " +
                        "WHERE SYSTEM.GYMS.USER_ID IS NULL AND SYSTEM.CITIES.USER_ID IS NULL";

        List<String> cities = new ArrayList<>();
        List<String> gyms = new ArrayList<>();

        Statement statement;
        Connection connection;
        try {
            connection = getOracleConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectQuery);

            while (rs.next()) {
                if (rs.getString(CITY_COLUMN_INDEX) == null) {
                    gyms.add(rs.getString(GYM_COLUMN_INDEX));
                } else if (rs.getString(GYM_COLUMN_INDEX) == null) {
                    cities.add(rs.getString(CITY_COLUMN_INDEX));
                }
            }
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException(ex);
        }

        req.setAttribute("gyms", gyms);
        req.setAttribute("cities", cities);

        RequestDispatcher rd = req.getRequestDispatcher("login-auth.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
