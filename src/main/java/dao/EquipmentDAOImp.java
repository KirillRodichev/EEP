package dao;

import dao.abstracts.DAO;
import dao.interfaces.EquipmentDAO;
import model.Equipment;
import model.LoadedEquipment;
import model.entity.EquipmentEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.IntegerType;
import services.GymService;
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
                    "FROM (SELECT * FROM GYMS_EQUIPMENT WHERE GYM_ID = :g_id AND EQUIPMENT_ID IN (:cond) ORDER BY EQUIPMENT_ID) a " +
                    "WHERE rownum < ((:page_num * :page_size) + 1)) " +
                    "WHERE r__ >= (((:page_num - 1) * :page_size) + 1)";
    private static final String GET_CONDITIONAL_EQ_IDS = "SELECT EQUIPMENT_ID from B_GROUPS_EQUIPMENT WHERE B_GROUP_ID IN (:cond)";
    private static final String GET_COND_EQ_IDS = "SELECT EQUIPMENT_ID FROM GYMS_EQUIPMENT WHERE GYM_ID = :id AND EQUIPMENT_ID IN (:cond)";
    private static final String SELECT_BY_GYM_ID = "SELECT EQUIPMENT_ID FROM GYMS_EQUIPMENT WHERE GYM_ID = :g_id";
    private static final String DELETE_G_RELATION = "DELETE FROM GYMS_EQUIPMENT WHERE GYM_ID = :g_id AND EQUIPMENT_ID = :eq_id";
    private static final String ADD_G_RELATION = "INSERT INTO GYMS_EQUIPMENT (EQUIPMENT_ID, GYM_ID) VALUES (:eq_id, :g_id)";

    @Override
    public void delete(EquipmentEntity entity) {
        Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        session.delete(entity);
        tx.commit();
        session.close();
    }

    @Override
    public void merge(EquipmentEntity entity) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.merge(entity);
        tx.commit();
        session.close();
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
    public void create(EquipmentEntity equipment, int gymID) {
        create(equipment);
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

    /*@Override
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
    }*/

    @Override
    public LoadedEquipment getIDsForSinglePage(int pageNumber, int pageSize, int gymID) {
        return getIDsForSinglePage(pageNumber, pageSize, gymID, null);
    }

    @Override
    public LoadedEquipment getIDsForSinglePage(int pageNumber, int pageSize, int gymID, List<String> filters) {
        int equipmentIDsCount;
        Session session = HibernateSessionFactory
                .getSessionFactory()
                .openSession();
        Transaction tx = session.beginTransaction();
        NativeQuery query = filters != null
                ? session.createSQLQuery(PAGINATION_SELECT_CONDITIONAL)
                : session.createSQLQuery(PAGINATION_SELECT);
        query.addScalar("EQUIPMENT_ID", IntegerType.INSTANCE);
        if (filters != null) {
            List<Integer> filteredEquipmentIDs = getFilteredEqIDs(filters, gymID);
            equipmentIDsCount = filteredEquipmentIDs.size();
            query.setParameterList("cond", filteredEquipmentIDs);
        } else {
            equipmentIDsCount = new GymService().get(gymID).getGymEquipment().size();
        }
        query.setParameter("g_id", gymID);
        query.setParameter("page_num", pageNumber);
        query.setParameter("page_size", pageSize);

        List<Integer> equipmentIDs = (List<Integer>) query.list();

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
                .addScalar("EQUIPMENT_ID", IntegerType.INSTANCE)
                .setParameter("g_id", id)
                .list();;
        tx.commit();
        session.close();
        return idList;
    }

    private List<Integer> getFilteredEqIDs(List<String> filters, int gymID) {
        List<Integer> filteredEqIDs = new ArrayList<>();
        if (filters != null) {
            if (filters.size() == 0) {
                List<EquipmentEntity> equipment = getAll();
                for (EquipmentEntity eq : equipment) {
                    filteredEqIDs.add(eq.getId());
                }
            } else {
                List<Integer> filteredByBodyGroups = getFilteredByBodyGroups(filters);
                filteredEqIDs = getFilteredByGym(filteredByBodyGroups, gymID);
            }
        }
        return filteredEqIDs;
    }

    private List<Integer> getFilteredByGym(List<Integer> filteredByBodyGroups, int gymID) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<Integer> filteredEqIDs = (List<Integer>) session
                .createSQLQuery(GET_COND_EQ_IDS)
                .addScalar("EQUIPMENT_ID", IntegerType.INSTANCE)
                .setParameterList("cond", filteredByBodyGroups)
                .setParameter("id", gymID)
                .list();
        tx.commit();
        session.close();
        return filteredEqIDs;
    }

    private List<Integer> getFilteredByBodyGroups(List<String> filters) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<Integer> filteredEqIDs = (List<Integer>) session
                .createSQLQuery(GET_CONDITIONAL_EQ_IDS)
                .addScalar("EQUIPMENT_ID", IntegerType.INSTANCE)
                .setParameterList("cond", filters)
                .list();
        tx.commit();
        session.close();
        return filteredEqIDs;
    }

    @Override
    public EquipmentEntity getById(int id) {
        Session session = HibernateSessionFactory
                .getSessionFactory()
                .openSession();
        session.beginTransaction();
        EquipmentEntity equipment = HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .get(EquipmentEntity.class, id);
        session.getTransaction().commit();
        session.close();
        return equipment;
    }

    @Override
    public List<EquipmentEntity> getAll() {
        Session session = HibernateSessionFactory
                .getSessionFactory()
                .openSession();
        session.beginTransaction();
        List<EquipmentEntity> all = (List<EquipmentEntity>) HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .createQuery("From EquipmentEntity")
                .list();
        session.getTransaction().commit();
        session.close();
        return all;
    }
}
