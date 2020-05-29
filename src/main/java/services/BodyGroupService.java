package services;

import dao.BodyGroupDAOImp;
import model.entity.BodyGroupEntity;
import model.entity.CityEntity;

import java.util.List;

public class BodyGroupService {

    private BodyGroupDAOImp bgDAOImp = new BodyGroupDAOImp();

    public BodyGroupService() {}

    public BodyGroupEntity get(int id) {
        return bgDAOImp.getById(id);
    }

    public int getIDByName(String name) { return bgDAOImp.getIDByName(name); }

    public List<Integer> getIdsListByEquipmentId(int eqID) { return bgDAOImp.getIdsListByEquipmentId(eqID); }

    public void create(BodyGroupEntity bodyGroup) {
        bgDAOImp.create(bodyGroup);
    }

    public void delete(BodyGroupEntity bodyGroup) {
        bgDAOImp.delete(bodyGroup);
    }

    public void update(BodyGroupEntity bodyGroup) {
        bgDAOImp.update(bodyGroup);
    }

    public void update(List<Integer> bgIDs, int eqID) { bgDAOImp.update(bgIDs, eqID); }

    public List<BodyGroupEntity> getAll() {
        return bgDAOImp.getAll();
    }

    public void addToEquip(List<Integer> bgIDs, int eqID) { bgDAOImp.addToEquip(bgIDs, eqID); }


}
