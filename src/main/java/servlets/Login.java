package servlets;

import constants.Columns;
import constants.DB;
import constants.DispatchAttrs;
import model.Gym;
import model.User;
import oracleConnection.OracleConnection;

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

@WebServlet("/login")
public class Login extends HttpServlet {

    private static final int CITY_NAME_COLUMN_INDEX = 2;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String selectUser =
                "SELECT * FROM SYSTEM.USERS WHERE USER_EMAIL = '" + req.getParameter("email") + "'";

        String selectGymId =
                "SELECT GYM_ID FROM SYSTEM.USERS_GYMS WHERE USER_ID = ?";

        String selectGym =
                "SELECT * FROM SYSTEM.GYMS WHERE GYM_ID = ?";

        String selectCities =
                "SELECT * FROM SYSTEM.CITIES";

        User user = null;
        Gym gym = null;
        ArrayList<String> cities = new ArrayList<>();
        try (Connection connection = getOracleConnection()) {
            Statement statementUser = connection.createStatement();
            ResultSet rsUser = statementUser.executeQuery(selectUser);
            while (rsUser.next()) {
                user = new User(
                        rsUser.getInt(Columns.USER_ID),
                        rsUser.getString(Columns.USER_NAME),
                        rsUser.getString(Columns.USER_EMAIL),
                        rsUser.getString(Columns.USER_PASSWORD),
                        rsUser.getString(Columns.USER_MODE)
                );
            }

            Statement statementCity = connection.createStatement();
            ResultSet rsCities = statementCity.executeQuery(selectCities);
            while (rsCities.next()) {
                cities.add(rsCities.getString(CITY_NAME_COLUMN_INDEX));
            }

            int gymId = DB.EMPTY_FIELD;
            if (user != null) {
                ResultSet rsGymId = OracleConnection.getSingleIntResultSet(selectGymId, connection, user.getId());
                while (rsGymId.next()) {
                    gymId = rsGymId.getInt(Columns.GYM_ID);
                }
            }

            if (gymId != DB.EMPTY_FIELD) {
                PreparedStatement psGym = connection.prepareStatement(selectGym);
                psGym.setInt(Columns.GYM_ID, gymId);
                ResultSet rsGym = psGym.executeQuery();
                while (rsGym.next()) {
                    gym = new Gym(
                            /*
                            what if field is NULL
                             */
                            rsGym.getInt(Columns.GYM_ID),
                            rsGym.getString(Columns.GYM_NAME),
                            rsGym.getString(Columns.GYM_WEBSITE),
                            rsGym.getString(Columns.GYM_WEBSITE_URL),
                            rsGym.getString(Columns.GYM_LOGO_PATH),
                            rsGym.getString(Columns.GYM_PHONE),
                            rsGym.getString(Columns.GYM_ADDRESS)
                    );
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        RequestDispatcher rd;
        req.setAttribute(DispatchAttrs.CITIES, cities);
        if (user != null) {
            req.setAttribute(DispatchAttrs.USER, user);
            req.setAttribute(DispatchAttrs.GYMS, Objects.requireNonNullElse(gym, null));
            rd = req.getRequestDispatcher("pages/cabinet.jsp");
        } else {
            rd = req.getRequestDispatcher("pages/nosuccess.jsp");
        }
        rd.forward(req, resp);
    }
}
