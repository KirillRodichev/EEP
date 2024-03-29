package controllers;

import constants.Columns;
import constants.DB;
import controllers.abstracts.DAOController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BodyGroupController extends DAOController<String, Set<Integer>> {

    private static final String SELECT_BY_EQ_ID = "SELECT B_GROUP_ID FROM B_GROUPS_EQUIPMENT WHERE EQUIPMENT_ID = ?";
    private static final String SELECT_BY_NAME = "SELECT B_GROUP_ID FROM BODY_GROUPS WHERE B_GROUP_NAME = ?";
    private static final String SELECT_BY_ID = "SELECT B_GROUP_NAME FROM BODY_GROUPS WHERE B_GROUP_ID = ?";
    private static final String SELECT_ALL = "SELECT B_GROUP_NAME FROM BODY_GROUPS";
    private static final String ADD_EQ_RELATION = "INSERT INTO B_GROUPS_EQUIPMENT (B_GROUP_ID, EQUIPMENT_ID)  VALUES (?, ?)";
    private static final String DELETE_EQ_RELATIONS = "DELETE FROM B_GROUPS_EQUIPMENT WHERE EQUIPMENT_ID = ?";

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
    public Set<Integer> update(Set<Integer> bodyGroups) throws SQLException {
        return null;
    }

    @Override
    public Set<Integer> update(Set<Integer> bodyGroups, int eqID) throws SQLException {
        PreparedStatement ps = getPreparedStatement(DELETE_EQ_RELATIONS);
        ps.setInt(Columns.FIRST, eqID);
        ps.execute();
        closePreparedStatement(ps);
        addToEquip(bodyGroups, eqID);
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

    public int getIDByName(String name) throws SQLException {
        PreparedStatement ps = getPreparedStatement(SELECT_BY_NAME);
        ps.setString(Columns.FIRST, name);
        ResultSet rs = ps.executeQuery();
        int id = DB.EMPTY_FIELD;
        while (rs.next()) {
            id = rs.getInt(Columns.FIRST);
        }
        return id;
    }

    public Set<Integer> getIdsListByEquipmentId(int id) throws SQLException {
        Set<Integer> idList = new HashSet<>();
        PreparedStatement ps = getPreparedStatement(SELECT_BY_EQ_ID);
        ps.setInt(Columns.FIRST, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            idList.add(rs.getInt(Columns.FIRST));
        }
        return idList;
    }

    public void addToEquip(Set<Integer> bgIDs, int eqID) throws SQLException {
        for (Integer id : bgIDs) {
            PreparedStatement ps = getPreparedStatement(ADD_EQ_RELATION);
            ps.setInt(Columns.FIRST, id);
            ps.setInt(Columns.SECOND, eqID);
            ps.execute();
            closePreparedStatement(ps);
        }
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void create(String entity) throws SQLException {

    }
}
