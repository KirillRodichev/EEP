package dao;

import dao.abstracts.DAO;
import dao.interfaces.UserDAO;
import model.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import utils.HibernateSessionFactory;

import java.util.List;

public class UserDAOImp extends DAO<UserEntity> implements UserDAO {
    private static final String SELECT_BY_EMAIL = "SELECT * FROM USERS WHERE USER_EMAIL = :email";
    private static final String SELECT_PASS_BY_ID = "SELECT USER_PASSWORD FROM USERS WHERE USER_ID = :u_id";

    @Override
    public UserEntity getByEmail(String email) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        NativeQuery query = session.createSQLQuery(SELECT_BY_EMAIL);
        query.setParameter("email", email);
        UserEntity res = (UserEntity) query.uniqueResult();
        tx.commit();
        session.close();
        return res;
    }

    @Override
    public String getPassword(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        NativeQuery query = session.createSQLQuery(SELECT_PASS_BY_ID);
        query.setParameter("u_id", id);
        String pass = (String) query.uniqueResult();
        tx.commit();
        session.close();
        return pass;
    }

    @Override
    public UserEntity getById(int id) {
        return HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .get(UserEntity.class, id);
    }

    @Override
    public List<UserEntity> getAll() {
        return (List<UserEntity>)  HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .createQuery("From UserEntity")
                .list();
    }
}
