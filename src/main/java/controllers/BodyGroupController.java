package controllers;

import constants.Columns;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BodyGroupController extends DAOController<String> {

    private static final String SELECT_BY_EQ_ID = "SELECT B_GROUP_ID FROM B_GROUPS_EQUIPMENT WHERE EQUIPMENT_ID = ?";
    private static final String SELECT_BY_ID = "SELECT B_GROUP_NAME FROM BODY_GROUPS WHERE B_GROUP_ID = ?";
    private static final String SELECT_ALL = "SELECT B_GROUP_NAME FROM BODY_GROUPS";

    @Override
    public List<String> getAll() throws SQLException {
        List<String> bodyGroups = new ArrayList<>();
        PreparedStatement ps = getPreparedStatement(SELECT_ALL);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            bodyGroups.add(rs.getString(Columns.FIRST));
        }
        closePreparedStatement(ps);
        return bodyGroups;
    }

    @Override
    public String update(String entity) {
        return null;
    }

    @Override
    public String getById(int id) throws SQLException {
        String bodyGroup = null;
        PreparedStatement ps = getPreparedStatement(SELECT_BY_ID);
        ps.setInt(Columns.FIRST, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            bodyGroup = rs.getString(Columns.FIRST);
        }
        return bodyGroup;
    }

    public List<Integer> getIdsListByEquipmentId(int id) throws SQLException {
        List<Integer> idList = new ArrayList<>();
        PreparedStatement ps = getPreparedStatement(SELECT_BY_EQ_ID);
        ps.setInt(Columns.FIRST, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            idList.add(rs.getInt(Columns.FIRST));
        }
        return idList;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void create(String entity) throws SQLException {

    }
}
