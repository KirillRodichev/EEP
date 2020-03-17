package controllers;

import constants.Columns;
import constants.DB;
import model.Gym;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GymController extends DAOController<Gym> {

    private static final String SELECT_BY_ID = "SELECT * FROM GYMS WHERE GYM_ID = ?";
    private static final String SELECT_ALL = "SELECT * FROM GYMS";
    private static final String SELECT_BY_USER_ID = "SELECT GYM_ID FROM USERS_GYMS WHERE USER_ID = ?";

    @Override
    public List<Gym> getAll() throws SQLException {
        List<Gym> gyms = new ArrayList<>();
        PreparedStatement ps = getPreparedStatement(SELECT_ALL);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            gyms.add(new Gym(
                    rs.getInt(Columns.GYM_ID),
                    rs.getString(Columns.GYM_NAME),
                    rs.getString(Columns.GYM_WEBSITE),
                    rs.getString(Columns.GYM_WEBSITE_URL),
                    rs.getString(Columns.GYM_LOGO_PATH),
                    rs.getString(Columns.GYM_PHONE),
                    rs.getString(Columns.GYM_ADDRESS)
            ));
        }
        closePreparedStatement(ps);
        return gyms;
    }

    @Override
    public Gym update(Gym gym) {
        return null;
    }

    @Override
    public Gym getById(int id) throws SQLException {
        Gym gym = null;
        PreparedStatement ps = getPreparedStatement(SELECT_BY_ID);
        ps.setInt(Columns.FIRST, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            gym = new Gym(
                    rs.getInt(Columns.GYM_ID),
                    rs.getString(Columns.GYM_NAME),
                    rs.getString(Columns.GYM_WEBSITE),
                    rs.getString(Columns.GYM_WEBSITE_URL),
                    rs.getString(Columns.GYM_LOGO_PATH),
                    rs.getString(Columns.GYM_PHONE),
                    rs.getString(Columns.GYM_ADDRESS)
            );
        }
        return gym;
    }

    public int getIdByUserId(int id) throws SQLException {
        int gymID = DB.EMPTY_FIELD;
        PreparedStatement ps = getPreparedStatement(SELECT_BY_USER_ID);
        ps.setInt(Columns.FIRST, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            gymID = rs.getInt(Columns.FIRST);
        }
        return gymID;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void create(Gym gym) throws SQLException {

    }
}
