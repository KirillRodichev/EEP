package dao.interfaces;

import java.util.List;

public interface BodyGroupDAO {

    void addToEquip(List<Integer> bgIDs, int eqID);

    void update(List<Integer> bgIDs, int eqID);

    int getIDByName(String name);

    List<Integer> getIdsListByEquipmentId(int eqID);
}
