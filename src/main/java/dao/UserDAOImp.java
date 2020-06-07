package dao;

import dao.abstracts.DAO;
import dao.interfaces.UserDAO;
import model.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import utils.HibernateSessionFactory;

import java.util.List;

public class UserDAOImp extends DAO<UserEntity> implements UserDAO {
    private static final String INSERT = "INSERT INTO USERS " +
                    "(USER_ID, USER_NAME, USER_EMAIL, USER_PASSWORD, USER_MODE) " +
                    "VALUES (USERS_SEQ.nextval, :name, :email, :pass, :mode)";

    @Override
    public void create(UserEntity entity) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        NativeQuery query = session.createSQLQuery(INSERT);
        query.setParameter("name", entity.getName());
        query.setParameter("email", entity.getEmail());
        query.setParameter("pass", entity.getPassword());
        query.setParameter("mode", entity.getMode());
        query.executeUpdate();
        tx.commit();
        session.close();
    }

    @Override
    public UserEntity getByEmail(String email) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from UserEntity where email = :email");
        query.setParameter("email", email);
        UserEntity user = (UserEntity) query.uniqueResult();
        tx.commit();
        session.close();
        return user;
    }

    @Override
    public String getPassword(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("select password from UserEntity where id = :id");
        query.setParameter("id", id);
        String pass = (String) query.uniqueResult();
        tx.commit();
        session.close();
        return pass;
    }

    @Override
    public UserEntity getById(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        UserEntity user = HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .get(UserEntity.class, id);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    @Override
    public List<UserEntity> getAll() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        List<UserEntity> all = (List<UserEntity>)  HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .createQuery("From UserEntity")
                .list();
        session.getTransaction().commit();
        session.close();
        return all;
    }
}
