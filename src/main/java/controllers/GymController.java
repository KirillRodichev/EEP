package controllers;

import constants.Columns;
import constants.DB;
import model.Equipment;
import model.EquipmentDTO;
import model.Gym;
import model.GymDTO;
import org.xml.sax.SAXException;
import xml.GymSSB;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GymController extends DAOController<Gym, GymDTO> {

    private static final String SELECT_BY_ID = "SELECT * FROM GYMS WHERE GYM_ID = ?";
    private static final String SELECT_ALL = "SELECT * FROM GYMS";
    private static final String SELECT_BY_USER_ID = "SELECT GYM_ID FROM USERS_GYMS WHERE USER_ID = ?";
    private static final String CREATE = "insert into GYMS " +
            "(GYM_ID, GYM_NAME, GYM_WEBSITE, GYM_WEBSITE_URL, GYM_LOGO_PATH, GYM_PHONE, GYM_ADDRESS) " +
            "VALUES (GYMS_SEQ.nextval, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_RELATED_EQ_IDS = "SELECT EQUIPMENT_ID FROM GYMS_EQUIPMENT WHERE GYM_ID = ?";
    private static final String SELECT_EQ_IDS_FOR_XML = "SELECT EQUIPMENT_ID FROM GYMS_EQUIPMENT WHERE GYM_ID = ? AND";
    private static final String L_BRACE = "(";
    private static final String R_BRACE = ")";
    private static final String W_SPACE = " ";

    private static final String UPDATE_NAME = "UPDATE GYMS SET GYM_NAME = ? WHERE GYM_ID = ?";
    private static final String UPDATE_LOGO_PATH = "UPDATE GYMS SET GYM_LOGO_PATH = ? WHERE GYM_ID = ?";
    private static final String UPDATE_WEBSITE_URL = "UPDATE GYMS SET GYM_WEBSITE_URL = ? WHERE GYM_ID = ?";
    private static final String UPDATE_WEBSITE = "UPDATE GYMS SET GYM_WEBSITE = ? WHERE GYM_ID = ?";
    private static final String UPDATE_PHONE = "UPDATE GYMS SET GYM_PHONE = ? WHERE GYM_ID = ?";
    private static final String UPDATE_ADDRESS = "UPDATE GYMS SET GYM_ADDRESS = ? WHERE GYM_ID = ?";

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
    public GymDTO update(GymDTO gym) throws SQLException {
        int id = gym.getId();
        updateName(gym.getName(), id);
        updateLogoPath(gym.getLogoPath(), id);
        updateWebsiteURL(gym.getWebsiteURL(), id);
        updateWebsite(gym.getWebsite(), id);
        updatePhone(gym.getPhone(), id);
        updateAddress(gym.getAddress(), id);
        updateEquipment(gym.getEquipment(), id);
        return null;
    }

    @Override
    public GymDTO update(GymDTO gym, int gymID) throws SQLException {
        updateName(gym.getName(), gymID);
        updateLogoPath(gym.getLogoPath(), gymID);
        updateWebsiteURL(gym.getWebsiteURL(), gymID);
        updateWebsite(gym.getWebsite(), gymID);
        updatePhone(gym.getPhone(), gymID);
        updateAddress(gym.getAddress(), gymID);
        updateEquipment(gym.getEquipment(), gymID);
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
                    rs.getString(Columns.GYM_LOGO_PATH),
                    rs.getString(Columns.GYM_WEBSITE),
                    rs.getString(Columns.GYM_WEBSITE_URL),
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

    public File createXML(int gymID, Set<Integer> BGFilters) throws SQLException, FileNotFoundException, JAXBException, SAXException {
        EquipmentController eqController = new EquipmentController();

        Set<Integer> gymMatchingEqIDs = new HashSet<>();
        Set<Integer> allEqIDs = eqController.getFilteredEqIDs(BGFilters);
        String conditionalEqIDsQuery = eqController.getConditionalEqIDsQuery(allEqIDs);

        String query = SELECT_EQ_IDS_FOR_XML + W_SPACE + L_BRACE + conditionalEqIDsQuery + R_BRACE;

        PreparedStatement ps = getPreparedStatement(query);
        ps.setInt(Columns.FIRST, gymID);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            gymMatchingEqIDs.add(rs.getInt(Columns.FIRST));
        }
        closePreparedStatement(ps);

        List<Equipment> equipment = new ArrayList<>();

        for (Integer id : gymMatchingEqIDs) {
            equipment.add(eqController.getDTObyID(id));
        }

        Gym gym = getById(gymID);
        GymDTO gymDTO = new GymDTO(
                gym.getId(),
                gym.getName(),
                gym.getLogoPath(),
                gym.getWebsiteURL(),
                gym.getWebsite(),
                gym.getPhone(),
                gym.getAddress(),
                equipment
        );

        return new GymSSB().exportXML(gymDTO);
    }

    private void updateName(String name, int id) throws SQLException {
        updateField(name, UPDATE_NAME, id);
    }

    private void updateLogoPath(String logoPath, int id) throws SQLException {
        updateField(logoPath, UPDATE_LOGO_PATH, id);
    }

    private void updateWebsiteURL(String websiteURL, int id) throws SQLException {
        updateField(websiteURL, UPDATE_WEBSITE_URL, id);
    }

    private void updateWebsite(String website, int id) throws SQLException {
        updateField(website, UPDATE_WEBSITE, id);
    }

    private void updatePhone(String phone, int id) throws SQLException {
        updateField(phone, UPDATE_PHONE, id);
    }

    private void updateAddress(String address, int id) throws SQLException {
        updateField(address, UPDATE_ADDRESS, id);
    }

    private void updateEquipment(List<Equipment> equipment, int gymID) throws SQLException {
        EquipmentController eController = new EquipmentController();
        List<Integer> oldRelatedEqIDs = getOldRelatedEqIDs(gymID);
        List<Integer> intersection = getIntersection(equipment, oldRelatedEqIDs);
        List<Integer> relIntersectionExclusion = getRelIntersectionExclusion(oldRelatedEqIDs, intersection);

        updateOrCreateEq(equipment, intersection, eController, gymID);
        removeExcludedRelations(relIntersectionExclusion, eController, gymID);
    }

    private void removeExcludedRelations(
            List<Integer> relIntersectionExclusion, EquipmentController eController, int gymID
    ) throws SQLException {
        for (Integer id : relIntersectionExclusion) {
            eController.remove(gymID, id);
        }
    }

    private void updateOrCreateEq(
            List<Equipment> equipment, List<Integer> intersection, EquipmentController eController, int gymID
    ) throws SQLException {
        for (Equipment eq : equipment) {
            if (intersection.contains(eq.getId())) {
                eController.update((EquipmentDTO) eq);
            } else {
                eController.create(eq, gymID, ((EquipmentDTO) eq).getBodyGroups());
            }
        }
    }

    private List<Integer> getIntersection(List<Equipment> equipment, List<Integer> oldRelatedEqIDs) {
        List<Integer> intersection = new ArrayList<>(oldRelatedEqIDs);
        List<Integer> updatingEqIDs = new ArrayList<>();
        for (Equipment eq : equipment) {
            updatingEqIDs.add(eq.getId());
        }
        for (Integer relatedEqID : oldRelatedEqIDs) {
            if (!updatingEqIDs.contains(relatedEqID)) {
                intersection.remove(relatedEqID);
            }
        }
        return intersection;
    }

    private List<Integer> getRelIntersectionExclusion(List<Integer> oldRelatedEqIDs, List<Integer> intersection) {
        List<Integer> relIntersectionExclusion = new ArrayList<>(oldRelatedEqIDs);
        for (Integer interID : intersection) {
            relIntersectionExclusion.remove(interID);
        }
        return relIntersectionExclusion;
    }

    private List<Integer> getOldRelatedEqIDs(int gymID) throws SQLException {
        List<Integer> oldRelatedEqIDs = new ArrayList<>();
        PreparedStatement ps = getPreparedStatement(SELECT_RELATED_EQ_IDS);
        ps.setInt(Columns.FIRST, gymID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            oldRelatedEqIDs.add(rs.getInt(Columns.FIRST));
        }
        closePreparedStatement(ps);
        return oldRelatedEqIDs;
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
}