package controllers;

import constants.Columns;
import constants.DB;
import model.Equipment;
import model.EquipmentDTO;
import model.LoadedEquipment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EquipmentController extends DAOController<Equipment, EquipmentDTO> {

    private static final String SELECT_ALL = "SELECT * FROM EQUIPMENT";
    private static final String SELECT_BY_ID = "SELECT * FROM EQUIPMENT WHERE EQUIPMENT_ID = ?";
    private static final String SELECT_BY_GYM_ID = "SELECT EQUIPMENT_ID FROM GYMS_EQUIPMENT WHERE GYM_ID = ?";
    private static final String DELETE_G_RELATION = "DELETE FROM GYMS_EQUIPMENT WHERE GYM_ID = ? AND EQUIPMENT_ID = ?";
    private static final String CREATE = "INSERT INTO EQUIPMENT " +
            "(EQUIPMENT_ID, EQUIPMENT_NAME, EQUIPMENT_DESCRIPTION, EQUIPMENT_IMG_PATH) " +
            "VALUES (?, ?, ?, ?)";
    private static final String ADD_G_RELATION = "INSERT INTO GYMS_EQUIPMENT (EQUIPMENT_ID, GYM_ID) VALUES (?, ?)";
    private static final String CREATE_ID = "SELECT EQUIPMENT_SEQ.nextval from dual";
    private static final String PAGINATION_SELECT =
            "SELECT EQUIPMENT_ID FROM (SELECT a.*, rownum r__ FROM (SELECT * FROM GYMS_EQUIPMENT WHERE GYM_ID = ? " +
                    "ORDER BY EQUIPMENT_ID) a WHERE rownum < ((? * ?) + 1)) WHERE r__ >= (((? - 1) * ?) + 1)";
    private static final String GET_EQ_IDS_COUNT = "SELECT COUNT(*) FROM GYMS_EQUIPMENT WHERE GYM_ID = ?";
    private static final String GET_CONDITIONAL_EQ_IDS = "SELECT EQUIPMENT_ID from B_GROUPS_EQUIPMENT WHERE ?;";
    private static final String PAGINATION_SELECT_CONDITIONAL =
            "SELECT EQUIPMENT_ID FROM (SELECT a.*, rownum r__ FROM (SELECT * FROM GYMS_EQUIPMENT WHERE GYM_ID = ? " +
                    "AND (?) ORDER BY EQUIPMENT_ID) a WHERE rownum < ((? * ?) + 1)) WHERE r__ >= (((? - 1) * ?) + 1)";
    private static final String SELECT_RELATED_GYMS = "SELECT GYM_ID FROM GYMS_EQUIPMENT WHERE EQUIPMENT_ID = ?";
    private static final String SELECT_RELATED_BGS_IDS = "SELECT B_GROUP_ID FROM B_GROUPS_EQUIPMENT WHERE EQUIPMENT_ID = ?";
    private static final String DELETE_BG_RELATION = "DELETE FROM B_GROUPS_EQUIPMENT WHERE B_GROUP_ID = ? AND EQUIPMENT_ID = ?";
    private static final String DELETE = "DELETE FROM EQUIPMENT WHERE EQUIPMENT_ID = ?";

    private static final String UPDATE_NAME = "UPDATE EQUIPMENT SET EQUIPMENT_NAME = ? WHERE EQUIPMENT_ID = ?";
    private static final String UPDATE_DESCRIPTION = "UPDATE EQUIPMENT SET EQUIPMENT_DESCRIPTION = ? WHERE EQUIPMENT_ID = ?";
    private static final String UPDATE_IMG_PATH = "UPDATE EQUIPMENT SET EQUIPMENT_IMG_PATH = ? WHERE EQUIPMENT_ID = ?";

    @Override
    public List<Equipment> getAll() throws SQLException {
        List<Equipment> equipment = new ArrayList<>();
        PreparedStatement ps = getPreparedStatement(SELECT_ALL);
        return getEquipment(equipment, ps);
    }

    public EquipmentDTO getDTObyID(int id) throws SQLException {
        Equipment equipment = getById(id);
        Set<Integer> relatedBGsIDs = getRelatedBGsIDs(id);
        Set<String> sRelatedBGsIDs = new HashSet<>();
        BodyGroupController bgController = new BodyGroupController();

        for (Integer relatedID : relatedBGsIDs) {
            sRelatedBGsIDs.add(bgController.getById(relatedID));
        }

        return new EquipmentDTO(
                equipment.getId(),
                equipment.getName(),
                equipment.getDescription(),
                equipment.getImgPath(),
                sRelatedBGsIDs
        );
    };

    public LoadedEquipment getIDsForSinglePage(int pageNumber, int pageSize, int gymID) throws SQLException {
        return getIDsForSinglePage(pageNumber, pageSize, gymID, null);
    }

    public LoadedEquipment getIDsForSinglePage(int pageNumber, int pageSize, int gymID, Set<Integer> filters)
            throws SQLException {
        int equipmentIDsCount = 0;
        String conditionalEqIDsQuery;
        LoadedEquipment res = new LoadedEquipment();

        List<Integer> equipmentIDs = new ArrayList<>();
        PreparedStatement psPagination = filters == null
                ? getPreparedStatement(PAGINATION_SELECT)
                : getPreparedStatement(PAGINATION_SELECT_CONDITIONAL);

        if (filters != null) {
            Set<Integer> filteredEquipmentIDs = getFilteredEqIDs(filters);
            conditionalEqIDsQuery = getConditionalEqIDsQuery(filteredEquipmentIDs);
            equipmentIDsCount = filteredEquipmentIDs.size();

            psPagination.setInt(Columns.FIRST, gymID);
            psPagination.setString(Columns.SECOND, conditionalEqIDsQuery);
            psPagination.setInt(Columns.THIRD, pageNumber);
            psPagination.setInt(Columns.FOURTH, pageSize);
            psPagination.setInt(Columns.FIFTH, pageNumber);
            psPagination.setInt(Columns.SIXTH, pageSize);
        } else {
            PreparedStatement psCount = getPreparedStatement(GET_EQ_IDS_COUNT);
            psCount.setInt(Columns.FIRST, gymID);
            ResultSet rs = psCount.executeQuery();
            while (rs.next()) {
                equipmentIDsCount = rs.getInt(Columns.FIRST);
            }

            psPagination.setInt(Columns.FIRST, gymID);
            psPagination.setInt(Columns.SECOND, pageNumber);
            psPagination.setInt(Columns.THIRD, pageSize);
            psPagination.setInt(Columns.FOURTH, pageNumber);
            psPagination.setInt(Columns.FIFTH, pageSize);
        }

        ResultSet rs = psPagination.executeQuery();
        while (rs.next()) {
            equipmentIDs.add(rs.getInt(Columns.FIRST));
        }
        closePreparedStatement(psPagination);

        res.setEquipmentNumber(equipmentIDsCount);
        res.setEquipmentIDsForSinglePage(equipmentIDs);
        return res;
    }

    public List<Integer> getIDsByGymId(int id) throws SQLException {
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
    public EquipmentDTO update(EquipmentDTO equipment) throws SQLException {
        int id = equipment.getId();
        updateName(equipment.getName(), id);
        updateDescription(equipment.getDescription(), id);
        updateImgPath(equipment.getImgPath(), id);
        updateBodyGroups(equipment.getBodyGroups(), id);
        return null;
    }

    @Override
    public EquipmentDTO update(EquipmentDTO equipment, int gymID) throws SQLException {

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
    public void delete(int eqID) throws SQLException {
        deleteGymsRelations(eqID);
        deleteBGsRelations(eqID);
        deleteEq(eqID);
    }

    public void remove(int gymID, int eqID) throws SQLException {
        PreparedStatement ps = getPreparedStatement(DELETE_G_RELATION);
        ps.setInt(Columns.FIRST, gymID);
        ps.setInt(Columns.SECOND, eqID);
        ps.executeQuery();
        closePreparedStatement(ps);
    }

    @Override
    public void create(Equipment equipment) throws SQLException {
        PreparedStatement ps = getPreparedStatement(CREATE);
        equipment.setId(createID());
        ps.setInt(Columns.FIRST, equipment.getId());
        ps.setString(Columns.SECOND, equipment.getName());
        ps.setString(Columns.THIRD, equipment.getDescription());
        ps.setString(Columns.FOURTH, equipment.getImgPath());
        ps.execute();
        closePreparedStatement(ps);
    }

    public void create(Equipment equipment, int gymID, Set<String> bodyGroups) throws SQLException {
        create(equipment);
        addToGym(equipment.getId(), gymID);
        new BodyGroupController().addToEquip(extractIDs(bodyGroups), equipment.getId());
    }

    public void addToGym(int eqID, int gymID) throws SQLException {
        PreparedStatement ps = getPreparedStatement(ADD_G_RELATION);
        ps.setInt(Columns.FIRST, eqID);
        ps.setInt(Columns.SECOND, gymID);
        ps.execute();
        closePreparedStatement(ps);
    }

    private List<Equipment> getEquipment(List<Equipment> equipment, PreparedStatement ps) throws SQLException {
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

    private int createID() throws SQLException {
        int id = DB.EMPTY_FIELD;
        PreparedStatement ps = getPreparedStatement(CREATE_ID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            id = rs.getInt(Columns.FIRST);
        }
        closePreparedStatement(ps);
        return id;
    }

    private void deleteEq(int eqID) throws SQLException {
        PreparedStatement ps = getPreparedStatement(DELETE);
        ps.setInt(Columns.FIRST, eqID);
        ps.executeQuery();
        closePreparedStatement(ps);
    }

    private void deleteGymsRelations(int eqID) throws SQLException {
        PreparedStatement ps = getPreparedStatement(SELECT_RELATED_GYMS);
        ps.setInt(Columns.FIRST, eqID);
        ResultSet rs = ps.executeQuery();
        List<Integer> gymIDs = new ArrayList<>();
        while (rs.next()) {
            gymIDs.add(rs.getInt(Columns.FIRST));
        }
        for (Integer gymID : gymIDs) {
            remove(gymID, eqID);
        }
    }

    private void deleteBGsRelations(int eqID) throws SQLException {
        Set<Integer> relatedBGsIDs = getRelatedBGsIDs(eqID);
        for (Integer bgID : relatedBGsIDs) {
            deleteBGRelation(bgID, eqID);
        }
    }

    private Set<Integer> getRelatedBGsIDs(int eqID) throws SQLException {
        Set<Integer> bgIDs = new HashSet<>();
        PreparedStatement ps = getPreparedStatement(SELECT_RELATED_BGS_IDS);
        ps.setInt(Columns.FIRST, eqID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            bgIDs.add(rs.getInt(Columns.FIRST));
        }
        return bgIDs;
    }

    private void deleteBGRelation(int bgID, int eqID) throws SQLException {
        PreparedStatement ps = getPreparedStatement(DELETE_BG_RELATION);
        ps.setInt(Columns.FIRST, bgID);
        ps.setInt(Columns.SECOND, eqID);
        ps.executeQuery();
        closePreparedStatement(ps);
    }

    public String getConditionalEqIDsQuery(Set<Integer> filteredEquipmentIDs) throws SQLException {
        StringBuilder conditionalEqIDsQuery = new StringBuilder();
        int counter = 0;
        for (Integer id : filteredEquipmentIDs) {
            if (counter == 0) {
                conditionalEqIDsQuery.append(" EQUIPMENT_ID = ");
            } else {
                conditionalEqIDsQuery.append(" OR EQUIPMENT_ID = ");
            }
            conditionalEqIDsQuery.append(id);
            counter++;
        }
        return conditionalEqIDsQuery.toString();
    }

    public Set<Integer> getFilteredEqIDs(Set<Integer> filters) throws SQLException {
        Set<Integer> filteredEqIDs = new HashSet<>();
        PreparedStatement ps;
        if (filters.size() == 0) {
            List<Equipment> equipment = new EquipmentController().getAll();
            for (Equipment eq : equipment) {
                filteredEqIDs.add(eq.getId());
            }
        } else {
            StringBuilder sFilters = new StringBuilder();
            int counter = 0;
            for (Integer filter : filters) {
                if (counter == 0) {
                    sFilters.append(" B_GROUP_ID = ");
                } else {
                    sFilters.append(" OR B_GROUP_ID = ");
                }
                sFilters.append(filter);
                counter++;
            }
            ps = getPreparedStatement(GET_CONDITIONAL_EQ_IDS);
            ps.setString(Columns.FIRST, sFilters.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                filteredEqIDs.add(rs.getInt(Columns.FIRST));
            }
            closePreparedStatement(ps);
        }
        return filteredEqIDs;
    }

    private void updateName(String name, int id) throws SQLException {
        updateField(name, UPDATE_NAME, id);
    }

    private void updateDescription(String description, int id) throws SQLException {
        updateField(description, UPDATE_DESCRIPTION, id);
    }

    private void updateImgPath(String imgPath, int id) throws SQLException {
        updateField(imgPath, UPDATE_IMG_PATH, id);
    }

    private void updateBodyGroups(Set<String> bodyGroups, int id) throws SQLException {
        if (bodyGroups != null) {
            if (bodyGroups.size() != 0) {
                new BodyGroupController().update(extractIDs(bodyGroups), id);
            }
        }
    }

    private void updateField(String field, String sql, int id) throws SQLException {
        if (field != null) {
            if (field.length() != 0) {
                PreparedStatement ps = getPreparedStatement(sql);
                ps.setString(Columns.FIRST, field);
                ps.setInt(Columns.SECOND, id);
                ps.execute();
                closePreparedStatement(ps);
            }
        }
    }

    private Set<Integer> extractIDs(Set<String> bodyGroups) throws SQLException {
        Set<Integer> bgIDs = new HashSet<>();
        BodyGroupController bgController = new BodyGroupController();
        for (String bodyGroup : bodyGroups) {
            try {
                bgIDs.add(Integer.parseInt(bodyGroup));
            } catch (NumberFormatException e) {
                bgIDs.add(bgController.getIDByName(bodyGroup));
            }
        }
        return bgIDs;
    }
}
