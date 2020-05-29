package dao;

import dao.abstracts.DAO;
import model.entity.CityEntity;
import utils.HibernateSessionFactory;

import java.util.List;

public class CityDAOImp extends DAO<CityEntity> {

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
