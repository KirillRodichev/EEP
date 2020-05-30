package dao.interfaces;

import model.entity.CityEntity;

public interface CityDAO {
    CityEntity getByGymID(int gymID);
}
