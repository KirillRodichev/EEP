package dao;

import controllers.GymController;
import dao.abstracts.DAO;
import dao.interfaces.GymDAO;
import model.entity.GymEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.xml.sax.SAXException;
import utils.HibernateSessionFactory;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class GymDAOImp extends DAO<GymEntity> implements GymDAO {
    private static final String SELECT_BY_USER_ID = "SELECT GYM_ID FROM USERS_GYMS WHERE USER_ID = :u_id";
    private static final String SELECT_ALL_NAMES = "SELECT GYM_NAME FROM GYMS";

    @Override
    public int getIdByUserId(int userID) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        int id = (int) session
                .createSQLQuery(SELECT_BY_USER_ID)
                .setParameter("u_id", userID)
                .uniqueResult();
        tx.commit();
        session.close();
        return id;
    }

    @Override
    public List<String> getAllNames() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<String> names = (List<String>) session
                .createSQLQuery(SELECT_ALL_NAMES)
                .list();
        tx.commit();
        session.close();
        return names;
    }

    @Override
    public void update(GymEntity gym, int gymID) {
        if (getById(gymID) != null) {
            gym.setId(gymID);
            super.update(gym);
        } else {
            create(gym);
        }
    }

    @Override
    public File createXML(int gymID, Set<Integer> BGFilters)
            throws SQLException, JAXBException, SAXException, FileNotFoundException
    {
        return new GymController().createXML(gymID, BGFilters);
    }

    @Override
    public GymEntity getById(int id) {
        return HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .get(GymEntity.class, id);
    }

    @Override
    public List<GymEntity> getAll() {
        return (List<GymEntity>) HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .createQuery("From GymEntity")
                .list();
    }
}
