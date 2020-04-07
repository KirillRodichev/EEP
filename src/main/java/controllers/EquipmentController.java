package controllers;

import constants.Columns;
import model.Equipment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipmentController extends DAOController<Equipment> {

    private static final String SELECT_ALL = "SELECT * FROM EQUIPMENT";
    private static final String SELECT_BY_ID = "SELECT * FROM EQUIPMENT WHERE EQUIPMENT_ID = ?";
    private static final String SELECT_BY_GYM_ID = "SELECT EQUIPMENT_ID FROM GYMS_EQUIPMENT WHERE GYM_ID = ?";
    private static final String DELETE_G_RELATION = "DELETE FROM GYMS_EQUIPMENT WHERE GYM_ID = ? AND EQUIPMENT_ID = ?";
    private static final String CREATE = "INSERT INTO EQUIPMENT " +
            "(EQUIPMENT_ID, EQUIPMENT_NAME, EQUIPMENT_DESCRIPTION, EQUIPMENT_IMG_PATH) " +
            "VALUES (EQUIPMENT_SEQ.nextval, ?, ?, ?)";
    private static final String ADD_G_RELATION = "INSERT INTO GYMS_EQUIPMENT (EQUIPMENT_ID, GYM_ID) VALUES (?, ?)";

    @Override
    public List<Equipment> getAll() throws SQLException {
        List<Equipment> equipment = new ArrayList<>();
        PreparedStatement ps = getPreparedStatement(SELECT_ALL);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            equipment.add(new Equipment(
                    rs.getInt(Columns.EQUIPMENT_ID),
                    rs.getString(Columns.EQUIPMENT_NAME),
                    rs.getString(Columns.EQUIPMENT_DESCRIPTION),
                    rs.getString(Columns.EQUIPMENT_IMG_PATH)
            ));
        }
        closePreparedStatement(ps);
        return equipment;
    }

    public List<Integer> getIdListByGymId(int id) throws SQLException {
        List<Integer> idList = new ArrayList<>();
        PreparedStatement ps = getPreparedStatement(SELECT_BY_GYM_ID);
        ps.setInt(Columns.FIRST, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            idList.add(rs.getInt(Columns.FIRST));
        }
        closePreparedStatement(ps);
        return idList;
    }

    @Override
    public Equipment update(Equipment equipment) {
        return null;
    }

    @Override
    public Equipment getById(int id) throws SQLException {
        Equipment equipment = null;
        PreparedStatement ps = getPreparedStatement(SELECT_BY_ID);
        ps.setInt(Columns.FIRST, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            equipment = new Equipment(
                    rs.getInt(Columns.EQUIPMENT_ID),
                    rs.getString(Columns.EQUIPMENT_NAME),
                    rs.getString(Columns.EQUIPMENT_DESCRIPTION),
                    rs.getString(Columns.EQUIPMENT_IMG_PATH)
            );
        }
        closePreparedStatement(ps);
        return equipment;
    }

    @Override
    public void delete(int id) throws SQLException {

    }

    public void delete(int gymID, int eqID) throws SQLException {
        PreparedStatement ps = getPreparedStatement(DELETE_G_RELATION);
        ps.setInt(Columns.FIRST, gymID);
        ps.setInt(Columns.SECOND, eqID);
        ps.executeQuery();
        closePreparedStatement(ps);
    }

    @Override
    public void create(Equipment equipment) throws SQLException {
        PreparedStatement ps = getPreparedStatement(CREATE);
        ps.setString(Columns.FIRST, equipment.getName());
        ps.setString(Columns.SECOND, equipment.getDescription());
        ps.setString(Columns.THIRD, equipment.getImgPath());
        ps.execute();
        closePreparedStatement(ps);
    }

    public void create(Equipment equipment, int gymID, String bodyGroups) throws SQLException {
        //create(equipment);
        System.out.println();
        System.out.println("gymID = " + gymID);
        System.out.println("bodyGroups = " + bodyGroups);
        System.out.println("equipment:");
        System.out.printf("\nid = %d\nname = %s\ndescr = %s\nimg = %s\n",
                equipment.getId(), equipment.getName(), equipment.getDescription(), equipment.getImgPath());
    }

    public void add(int eqID, int gymID) throws SQLException {
        PreparedStatement ps = getPreparedStatement(ADD_G_RELATION);
        ps.setInt(Columns.FIRST, eqID);
        ps.setInt(Columns.SECOND, gymID);
        ps.execute();
        closePreparedStatement(ps);
    }
}
