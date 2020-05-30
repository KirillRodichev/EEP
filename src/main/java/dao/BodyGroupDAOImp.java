package dao;

import dao.abstracts.DAO;
import dao.interfaces.BodyGroupDAO;
import model.entity.BodyGroupEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.type.IntegerType;
import utils.HibernateSessionFactory;

import java.util.List;

public class BodyGroupDAOImp extends DAO<BodyGroupEntity> implements BodyGroupDAO {
    private static final String DELETE_EQ_RELATIONS = "DELETE FROM B_GROUPS_EQUIPMENT WHERE EQUIPMENT_ID = :eq_id";
    private static final String ADD_EQ_RELATION = "INSERT INTO B_GROUPS_EQUIPMENT (B_GROUP_ID, EQUIPMENT_ID) VALUES (:bg_id, :eq_id)";
    private static final String SELECT_BY_EQ_ID = "SELECT B_GROUP_ID FROM B_GROUPS_EQUIPMENT WHERE EQUIPMENT_ID = :eq_id";

    @Override
    public void addToEquip(List<Integer> bgIDs, int eqID) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        NativeQuery query = session.createSQLQuery(ADD_EQ_RELATION);
        for (int bgID : bgIDs) {
            query.setParameter("bg_id", bgID);
            query.setParameter("eq_id", eqID);
        }
        query.executeUpdate();
        tx.commit();
        session.close();
    }

    @Override
    public void update(List<Integer> bgIDs, int eqID) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        NativeQuery query = session.createSQLQuery(DELETE_EQ_RELATIONS);
        query.setParameter("eq_id", eqID);
        query.executeUpdate();
        tx.commit();
        session.close();
    }

    @Override
    public int getIDByName(String name) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("select id from BodyGroupEntity where name = :name");
        query.setParameter("name", name);
        int id = (int) query.uniqueResult();
        tx.commit();
        session.close();
        return id;
    }

    @Override
    public List<Integer> getIdsListByEquipmentId(int eqID) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        NativeQuery query = session.createSQLQuery(SELECT_BY_EQ_ID);
        query.addScalar("B_GROUP_ID", IntegerType.INSTANCE);
        query.setParameter("eq_id", eqID);
        query.addScalar("B_GROUP_ID", IntegerType.INSTANCE);
        List<Integer> res = (List<Integer>) query.list();
        tx.commit();
        session.close();
        return res;
    }

    @Override
    public BodyGroupEntity getById(int id) {
        return HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .get(BodyGroupEntity.class, id);
    }

    @Override
    public List<BodyGroupEntity> getAll() {
        return (List<BodyGroupEntity>)  HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .createQuery("From BodyGroupEntity")
                .list();
    }
}
