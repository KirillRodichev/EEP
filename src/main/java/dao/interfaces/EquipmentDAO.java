package dao.interfaces;

import model.Equipment;
import model.LoadedEquipment;
import model.entity.EquipmentEntity;

import java.util.List;

public interface EquipmentDAO {
    void remove(int gymID, int eqID);

    void create(EquipmentEntity equipment, int gymID);

    void merge(EquipmentEntity equipment);

    void addToGym(int eqID, int gymID);

    //String getConditionalEqIDsQuery(List<Integer> filteredEquipmentIDs);

    LoadedEquipment getIDsForSinglePage(int pageNumber, int pageSize, int gymID);

    LoadedEquipment getIDsForSinglePage(int pageNumber, int pageSize, int gymID, List<String> filters);

    List<Integer> getIDsByGymId(int id);
}
