package dao;

import controllers.GymController;
import dao.abstracts.DAO;
import dao.interfaces.GymDAO;
import model.entity.GymEntity;
import model.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.xml.sax.SAXException;
import utils.HibernateSessionFactory;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import static utils.DB.ableToUpdate;

public class GymDAOImp extends DAO<GymEntity> implements GymDAO {

    @Override
    public void merge(GymEntity prev, GymEntity cur) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        mergeSetup(prev, cur);
        session.merge(cur);
        tx.commit();
        session.close();
    }

    @Override
    public int getIdByUserId(int userID) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from UserEntity where id = :id");
        UserEntity user = (UserEntity) query.setParameter("id", userID).uniqueResult();
        int gymID = user.getUserGym().getId();
        tx.commit();
        session.close();
        return gymID;
    }

    @Override
    public List<String> getAllNames() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<String> names = (List<String>) session
                .createQuery("select name from GymEntity")
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

    private void mergeSetup(GymEntity prev, GymEntity cur) {
        if (!ableToUpdate(cur.getName())) cur.setName(prev.getName());
        if (!ableToUpdate(cur.getLogoPath())) cur.setLogoPath(prev.getLogoPath());
        if (!ableToUpdate(cur.getWebsiteURL())) cur.setWebsiteURL(prev.getWebsiteURL());
        if (!ableToUpdate(cur.getWebsite())) cur.setWebsite(prev.getWebsite());
        if (!ableToUpdate(cur.getPhone())) cur.setPhone(prev.getPhone());
        if (!ableToUpdate(cur.getAddress())) cur.setAddress(prev.getAddress());
        cur.setGymUsers(prev.getGymUsers());
        cur.setGymEquipment(prev.getGymEquipment());
    }
}
