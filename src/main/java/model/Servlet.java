package model;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

import static OracleConnection.OracleConnection.getOracleConnection;

@WebServlet("/login")
public class Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("doGet");

        /*int id = Integer.parseInt(req.getParameter("id"));
        String lastName = req.getParameter("lastName");
        String secondName = req.getParameter("secondName");
        int salary = Integer.parseInt(req.getParameter("salary"));*/

        int id = 10;
        String lastName = "Last";
        String secondName = "Second";
        int salary = 1900;

        String insertStatement = "INSERT INTO SYSTEM.EMPLOYEES " +
                "(EMPLOYEE_ID, LAST_NAME, FIRST_NAME, SALARY) " +
                "VALUES " +
                "(?, ?, ?, ?)";

        try {
            Connection connection = getOracleConnection();

            PreparedStatement ps = connection.prepareStatement(insertStatement);
            ps .setInt(1, id);
            ps.setString(2, lastName);
            ps.setString(3, secondName);
            ps.setInt(4, salary);

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
