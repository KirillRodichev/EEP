package dao;

import dao.abstracts.DAO;
import dao.interfaces.CityDAO;
import model.entity.CityEntity;
import model.entity.GymEntity;
import org.hibernate.Session;
import utils.HibernateSessionFactory;

import java.util.List;

public class CityDAOImp extends DAO<CityEntity> implements CityDAO {

    @Override
    public CityEntity getByGymID(int gymID) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        CityEntity city = HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .get(GymEntity.class, gymID)
                .getGymCity();
        session.getTransaction().commit();
        session.close();
        return city;
    }

    @Override
    public CityEntity getById(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        CityEntity city = HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .get(CityEntity.class, id);
        session.getTransaction().commit();
        session.close();
        return city;
    }

    @Override
    public List<CityEntity> getAll() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        List<CityEntity> all = (List<CityEntity>) HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .createQuery("From CityEntity")
                .list();
        session.getTransaction().commit();
        session.close();
        return all;
    }
}
