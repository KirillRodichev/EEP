package controllers;

import constants.Columns;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CityController extends DAOController<String, String> {

    private static final String SELECT_ALL = "SELECT CITY_NAME FROM CITIES";
    private static final String SELECT_BY_ID = "SELECT CITY_NAME FROM CITIES";

    @Override
    public List<String> getAll() throws SQLException {
        List<String> cities = new ArrayList<>();
        PreparedStatement ps = getPreparedStatement(SELECT_ALL);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            cities.add(rs.getString(Columns.FIRST));
        }
        closePreparedStatement(ps);
        return cities;
    }

    @Override
    public String update(String entity) throws SQLException {
        return null;
    }

    @Override
    public String update(String entity, int id) {
        return null;
    }

    @Override
    public String getById(int id) throws SQLException {
        String city = null;
        PreparedStatement ps = getPreparedStatement(SELECT_BY_ID);
        ps.setInt(Columns.FIRST, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            city = rs.getString(Columns.FIRST);
        }
        return city;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void create(String entity) throws SQLException {

    }
}
