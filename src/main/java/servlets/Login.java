package servlets;

import constants.Columns;
import constants.DispatchAttrs;
import constants.ErrorMsg;
import model.Gym;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

import static oracleConnection.OracleConnection.getOracleConnection;
import static utils.Utils.*;

@WebServlet("/login")
public class Login extends HttpServlet {

    private User loadUser(Connection con, String sql) throws SQLException, RuntimeException {
        User user = null;
        Statement statementUser = con.createStatement();
        ResultSet rsUser = statementUser.executeQuery(sql);
        while (rsUser.next()) {
            user = new User(
                    rsUser.getInt(Columns.USER_ID),
                    rsUser.getString(Columns.USER_NAME),
                    rsUser.getString(Columns.USER_EMAIL),
                    rsUser.getString(Columns.USER_PASSWORD),
                    rsUser.getString(Columns.USER_MODE)
            );
        }
        if (user == null) {
            throw new RuntimeException(ErrorMsg.USER_NOT_FOUND);
        }
        return user;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String selectUser =
                "SELECT * FROM USERS WHERE USER_EMAIL = '" + req.getParameter("email") + "'";

        String selectGymID = "SELECT GYM_ID FROM USERS_GYMS WHERE USER_ID = ?";

        String selectGym = "SELECT * FROM GYMS WHERE GYM_ID = ?";

        String selectCities = "SELECT CITY_NAME FROM CITIES";

        String selectGyms = "SELECT GYM_NAME FROM GYMS";

        User user = null;
        Gym gym = null;
        ArrayList<String> cities = new ArrayList<>();
        ArrayList<String> gyms = new ArrayList<>();
        RequestDispatcher rd;
        try (Connection connection = getOracleConnection()) {

            user = loadUser(connection, selectUser);

            loadList(connection, selectCities, cities);
            loadList(connection, selectGyms, gyms);

            int gymID = getGymID(connection, selectGymID, user);

            gym = loadGym(connection, selectGym, gymID);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            req.setAttribute(ErrorMsg.ERROR_MESSAGE, ErrorMsg.USER_ALREADY_EXISTS);
            rd = req.getRequestDispatcher("pages/error.jsp");
            rd.forward(req, resp);
        }

        forwardToCabinet(req, resp,user, cities, gyms, gym);
    }
}
