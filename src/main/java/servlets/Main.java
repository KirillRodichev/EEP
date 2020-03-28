package servlets;

import connection.OracleConnectionPool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(
        urlPatterns = { "/index.jsp" },
        initParams =
                {
                        @WebInitParam(name = "allowedTypes", value = "png, css, js, ico, jpg")
                }
)

public class Main extends HttpServlet {

    @Override
    public void init() throws ServletException {
        try {
            OracleConnectionPool.create();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("login-auth.jsp");
    }
}
