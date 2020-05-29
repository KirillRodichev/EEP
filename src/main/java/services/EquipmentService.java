package services;

import dao.EquipmentDAOImp;
import model.LoadedEquipment;
import model.entity.CityEntity;
import model.entity.EquipmentEntity;

import java.util.List;

public class EquipmentService {

    private EquipmentDAOImp eqDAOImp = new EquipmentDAOImp();

    public EquipmentService() {}

    public EquipmentEntity get(int id) {
        return eqDAOImp.getById(id);
    }

    public String getConditionalEqIDsQuery(List<Integer> filteredEquipmentIDs) {
        return eqDAOImp.getConditionalEqIDsQuery(filteredEquipmentIDs);
    }

    public LoadedEquipment getIDsForSinglePage(int pageNumber, int pageSize, int gymID) {
        return eqDAOImp.getIDsForSinglePage(pageNumber, pageSize, gymID);
    }

    public LoadedEquipment getIDsForSinglePage(int pageNumber, int pageSize, int gymID, List<Integer> filters) {
        return eqDAOImp.getIDsForSinglePage(pageNumber, pageSize, gymID, filters);
    }

    public List<Integer> getIDsByGymId(int id) { return eqDAOImp.getIDsByGymId(id); }

    public List<Integer> getFilteredEqIDs(List<Integer> filters) { return eqDAOImp.getFilteredEqIDs(filters); }

    public List<EquipmentEntity> getAll() {
        return eqDAOImp.getAll();
    }

    public void remove(int gymID, int eqID) { eqDAOImp.remove(gymID, eqID); }

    public void create(EquipmentEntity eq) {
        eqDAOImp.create(eq);
    }

    public void create(EquipmentEntity equipment, int gymID) { eqDAOImp.create(equipment, gymID); }

    public void delete(EquipmentEntity eq) {
        eqDAOImp.delete(eq);
    }

    public void update(EquipmentEntity eq) {
        eqDAOImp.update(eq);
    }

    public void addToGym(int eqID, int gymID) { eqDAOImp.addToGym(eqID, gymID); }
}