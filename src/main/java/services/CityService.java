package services;

import dao.CityDAOImp;
import model.entity.CityEntity;

import java.util.List;

public class CityService {

    private CityDAOImp cityDAO = new CityDAOImp();

    public CityService() {}

    public CityEntity get(int id) {
        return cityDAO.getById(id);
    }

    public void create(CityEntity city) {
        cityDAO.create(city);
    }

    public void delete(CityEntity city) {
        cityDAO.delete(city);
    }

    public void update(CityEntity city) {
        cityDAO.update(city);
    }

    public List<CityEntity> getAll() {
        return cityDAO.getAll();
    }
}
