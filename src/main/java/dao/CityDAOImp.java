package dao;

import dao.abstracts.DAO;
import dao.interfaces.CityDAO;
import model.entity.CityEntity;
import model.entity.GymEntity;
import utils.HibernateSessionFactory;

import java.util.List;

public class CityDAOImp extends DAO<CityEntity> implements CityDAO {

    @Override
    public CityEntity getByGymID(int gymID) {
        return HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .get(GymEntity.class, gymID)
                .getGymCity();
    }

    @Override
    public CityEntity getById(int id) {
        return HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .get(CityEntity.class, id);
    }

    @Override
    public List<CityEntity> getAll() {
        return (List<CityEntity>)  HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .createQuery("From CityEntity")
                .list();
    }
}
