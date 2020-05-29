package dao;

import dao.abstracts.DAO;
import dao.interfaces.EquipmentDAO;
import model.LoadedEquipment;
import model.entity.BodyGroupEntity;
import model.entity.EquipmentEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import utils.HibernateSessionFactory;

import java.util.ArrayList;
import java.util.List;

public class EquipmentDAOImp extends DAO<EquipmentEntity> implements EquipmentDAO {
    private static final String PAGINATION_SELECT =
            "SELECT EQUIPMENT_ID " +
                    "FROM (SELECT a.*, rownum r__ " +
                    "FROM (SELECT * FROM GYMS_EQUIPMENT WHERE GYM_ID = :g_id ORDER BY EQUIPMENT_ID) a " +
                    "WHERE rownum < ((:page_num * :page_size) + 1)) " +
                    "WHERE r__ >= (((:page_num - 1) * :page_size) + 1)";
    private static final String PAGINATION_SELECT_CONDITIONAL =
            "SELECT EQUIPMENT_ID " +
                    "FROM (SELECT a.*, rownum r__ " +
                    "FROM (SELECT * FROM GYMS_EQUIPMENT WHERE GYM_ID = :g_id AND (:cond) ORDER BY EQUIPMENT_ID) a " +
                    "WHERE rownum < ((:page_num * :page_size) + 1)) " +
                    "WHERE r__ >= (((:page_num - 1) * :page_size) + 1)";
    private static final String GET_CONDITIONAL_EQ_IDS = "SELECT EQUIPMENT_ID from B_GROUPS_EQUIPMENT WHERE :cond";
    private static final String GET_EQ_IDS_COUNT = "SELECT COUNT(*) FROM GYMS_EQUIPMENT WHERE GYM_ID = :g_id";
    private static final String SELECT_BY_GYM_ID = "SELECT EQUIPMENT_ID FROM GYMS_EQUIPMENT WHERE GYM_ID = :g_id";
    private static final String DELETE_G_RELATION = "DELETE FROM GYMS_EQUIPMENT WHERE GYM_ID = :g_id AND EQUIPMENT_ID = :eq_id";
    private static final String ADD_G_RELATION = "INSERT INTO GYMS_EQUIPMENT (EQUIPMENT_ID, GYM_ID) VALUES (:eq_id, :g_id)";
    private static final String CREATE_ID = "SELECT EQUIPMENT_SEQ.nextval from dual";

    @Override
    public void update(EquipmentEntity entity) {
        EquipmentEntity equipment = getById(entity.getId());
        final String name = entity.getName();
        final String description = entity.getDescription();
        final String imgPath = entity.getImgPath();
        final List<BodyGroupEntity> bodyGroups = entity.getBodyGroups();

        if (ableToUpdate(name)) equipment.setName(name);
        if (ableToUpdate(description)) equipment.setDescription(description);
        if (ableToUpdate(imgPath)) equipment.setImgPath(imgPath);
        if (ableToUpdate(bodyGroups)) equipment.setBodyGroups(bodyGroups);

        super.update(equipment);
    }

    @Override
    public void remove(int gymID, int eqID) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        NativeQuery query = session.createSQLQuery(DELETE_G_RELATION);
        query.setParameter("g_id", gymID);
        query.setParameter("eq_id", eqID);
        query.executeUpdate();
        tx.commit();
        session.close();
    }

    @Override
    public void create(EquipmentEntity entity) {
        entity.setId(createID());
        super.create(entity);
    }

    @Override
    public void create(EquipmentEntity equipment, int gymID) {
        create(equipment);
        addToGym(equipment.getId(), gymID);
    }

    @Override
    public void addToGym(int eqID, int gymID) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        NativeQuery query = session.createSQLQuery(ADD_G_RELATION);
        query.setParameter("eq_id", eqID);
        query.setParameter("g_id", gymID);
        query.executeUpdate();
        tx.commit();
        session.close();
    }

    @Override
    public String getConditionalEqIDsQuery(List<Integer> filteredEquipmentIDs) {
        StringBuilder conditionalEqIDsQuery = new StringBuilder();
        int counter = 0;
        for (Integer id : filteredEquipmentIDs) {
            String subCond = counter == 0 ? " EQUIPMENT_ID = " : " OR EQUIPMENT_ID = ";
            conditionalEqIDsQuery.append(subCond);
            conditionalEqIDsQuery.append(id);
            counter++;
        }
        return conditionalEqIDsQuery.toString();
    }

    @Override
    public LoadedEquipment getIDsForSinglePage(int pageNumber, int pageSize, int gymID) {
        return getIDsForSinglePage(pageNumber, pageSize, gymID, null);
    }

    @Override
    public LoadedEquipment getIDsForSinglePage(int pageNumber, int pageSize, int gymID, List<Integer> filters) {
        int equipmentIDsCount;
        Session session = HibernateSessionFactory
                .getSessionFactory()
                .openSession();
        Transaction tx = session.beginTransaction();
        NativeQuery query = filters != null
                ? session.createSQLQuery(PAGINATION_SELECT_CONDITIONAL)
                : session.createSQLQuery(PAGINATION_SELECT);
        if (filters != null) {
            List<Integer> filteredEquipmentIDs = getFilteredEqIDs(filters);
            String conditionalEqIDsQuery = getConditionalEqIDsQuery(filteredEquipmentIDs);
            equipmentIDsCount = filteredEquipmentIDs.size();
            query.setParameter("cond", conditionalEqIDsQuery);
        } else {
            equipmentIDsCount = (int) session
                    .createSQLQuery(GET_EQ_IDS_COUNT)
                    .setParameter("g_id", gymID)
                    .uniqueResult();
        }
        query.setParameter("g_id", gymID);
        query.setParameter("page_num", pageNumber);
        query.setParameter("page_size", pageSize);

        List<Integer> equipmentIDs = (List<Integer>)query.list();

        LoadedEquipment res = new LoadedEquipment();
        res.setEquipmentNumber(equipmentIDsCount);
        res.setEquipmentIDsForSinglePage(equipmentIDs);

        tx.commit();
        session.close();

        return res;
    }

    @Override
    public List<Integer> getIDsByGymId(int id) {
        Session session = HibernateSessionFactory
                .getSessionFactory()
                .openSession();
        Transaction tx = session.beginTransaction();
        List<Integer> idList = (List<Integer>)session
                .createSQLQuery(SELECT_BY_GYM_ID)
                .setParameter("g_id", id)
                .list();;
        tx.commit();
        session.close();
        return idList;
    }

    @Override
    public List<Integer> getFilteredEqIDs(List<Integer> filters) {
        List<Integer> filteredEqIDs = new ArrayList<>();
        if (filters != null) {
            if (filters.size() == 0) {
                List<EquipmentEntity> equipment = getAll();
                for (EquipmentEntity eq : equipment) {
                    filteredEqIDs.add(eq.getId());
                }
            } else {
                StringBuilder sFilters = new StringBuilder();
                int counter = 0;
                for (Integer filter : filters) {
                    String subCond = counter == 0 ? " B_GROUP_ID = " : " OR B_GROUP_ID = ";
                    sFilters.append(subCond);
                    sFilters.append(filter);
                    counter++;
                }
                Session session = HibernateSessionFactory.getSessionFactory().openSession();
                Transaction tx = session.beginTransaction();
                NativeQuery query = session.createSQLQuery(GET_CONDITIONAL_EQ_IDS);
                query.setParameter("cond", sFilters.toString());
                filteredEqIDs = (List<Integer>)query.list();
                tx.commit();
                session.close();
            }
        }
        return filteredEqIDs;
    }

    @Override
    public EquipmentEntity getById(int id) {
        return HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .get(EquipmentEntity.class, id);
    }

    @Override
    public List<EquipmentEntity> getAll() {
        return (List<EquipmentEntity>)  HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .createQuery("From EquipmentEntity")
                .list();
    }

    private int createID() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        int id = (int) session.createSQLQuery(CREATE_ID).uniqueResult();
        tx.commit();
        session.close();
        return id;
    }

    private boolean ableToUpdate(String field) {
        if (field != null) {
            return field.length() != 0;
        }
        return false;
    }

    private boolean ableToUpdate(List<BodyGroupEntity> field) {
        if (field != null) {
            return field.size() != 0;
        }
        return false;
    }
}
