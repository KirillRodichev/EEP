package utils;

import constants.Columns;
import constants.DB;
import constants.DispatchAttrs;
import model.Gym;
import model.User;
import oracleConnection.OracleConnection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class Utils {
    public static void loadList(Connection con, String sql, ArrayList<String> list) throws SQLException {
        Statement statementCity = con.createStatement();
        ResultSet rsCities = statementCity.executeQuery(sql);
        while (rsCities.next()) {
            list.add(rsCities.getString(Columns.FIRST));
        }
    }

    public static int getGymID(Connection con, String sql, User user) throws SQLException {
        int gymID = DB.EMPTY_FIELD;
        if (user != null) {
            ResultSet rsGymId = OracleConnection.getSingleIntResultSet(sql, con, user.getId());
            while (rsGymId.next()) {
                gymID = rsGymId.getInt(Columns.GYM_ID);
            }
        }
        return gymID;
    }

    public static Gym loadGym(Connection con, String sql, int gymID) throws SQLException {
        Gym gym = null;
        if (gymID != DB.EMPTY_FIELD) {
            PreparedStatement psGym = con.prepareStatement(sql);
            psGym.setInt(Columns.GYM_ID, gymID);
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
        return gym;
    }

    public static void forwardToCabinet(
            HttpServletRequest req,
            HttpServletResponse resp,
            User user,
            ArrayList<String> cities,
            ArrayList<String> gyms,
            Gym gym
    ) throws ServletException, IOException {
        RequestDispatcher rd;
        req.setAttribute(DispatchAttrs.CITIES, cities);
        req.setAttribute(DispatchAttrs.GYMS, gyms);
        req.setAttribute(DispatchAttrs.USER, user);
        req.setAttribute(DispatchAttrs.GYM, Objects.requireNonNullElse(gym, null));
        rd = req.getRequestDispatcher("pages/cabinet.jsp");
        rd.forward(req, resp);
    }

    public static void forwardToCabinet(
            HttpServletRequest req,
            HttpServletResponse resp,
            User user,
            ArrayList<String> cities,
            ArrayList<String> gyms
    ) throws ServletException, IOException {
        RequestDispatcher rd;
        req.setAttribute(DispatchAttrs.CITIES, cities);
        req.setAttribute(DispatchAttrs.USER, user);
        req.setAttribute(DispatchAttrs.GYMS, gyms);
        rd = req.getRequestDispatcher("pages/cabinet.jsp");
        rd.forward(req, resp);
    }
}
