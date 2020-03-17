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
        return equipment;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void create(Equipment equipment) throws SQLException {

    }
}
