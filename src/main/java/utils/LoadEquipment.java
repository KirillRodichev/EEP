package utils;

import controllers.BodyGroupController;
import controllers.EquipmentController;
import model.Equipment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoadEquipment {

    public static void loadData(
            List<Equipment> equipment, Map<Integer, List<String>> map, List<Equipment> rest, int gymID,
            EquipmentController equipmentController, BodyGroupController bodyGroupController
    ) throws SQLException {
        List<Integer> equipmentIDs = equipmentController.getIdListByGymId(gymID);
        loadEquipment(equipmentController, equipmentIDs, equipment);
        loadMap(equipment, map, bodyGroupController);
        loadRest(equipmentIDs, rest);
    }

    private static void loadEquipment(
            EquipmentController equipmentController, List<Integer> IDs, List<Equipment> equipment)
            throws SQLException {
        for (Integer id : IDs) {
            equipment.add(equipmentController.getById(id));
        }
    }

    private static void loadRest(List<Integer> existingIDs, List<Equipment> rest) throws SQLException {
        List<Equipment> all = new ArrayList<>(rest);
        for (Equipment eq : all) {
            for (int id : existingIDs) {
                if (eq.getId() == id) {
                    rest.remove(eq);
                    break;
                }
            }
        }
    }

    private static void loadMap(
            List<Equipment> equipment, Map<Integer, List<String>> map, BodyGroupController bodyGroupController
    ) throws SQLException {
        List<List<Integer>> bodyGroupsIDsList = loadBodyGroupsIDs(bodyGroupController, equipment);
        List<List<String>> bodyGroupsList = loadBodyGroups(bodyGroupController, bodyGroupsIDsList);
        for (int i = 0; i < equipment.size(); i++) {
            map.put(equipment.get(i).getId(), bodyGroupsList.get(i));
        }
    }

    private static List<List<Integer>> loadBodyGroupsIDs(BodyGroupController controller, List<Equipment> equipment
    ) throws SQLException {
        List<List<Integer>> IDsList = new ArrayList<>();
        for (Equipment eq : equipment) {
            List<Integer> IDs = controller.getIdsListByEquipmentId(eq.getId());
            IDsList.add(IDs);
        }
        return IDsList;
    }

    private static List<List<String>> loadBodyGroups(BodyGroupController controller, List<List<Integer>> IDsList
    ) throws SQLException {
        List<List<String>> bodyGroupsList = new ArrayList<>();
        for (List<Integer> idList : IDsList) {
            List<String> bodyGroups = new ArrayList<>();
            for (Integer id : idList) {
                bodyGroups.add(controller.getById(id));
            }
            bodyGroupsList.add(new ArrayList<>(bodyGroups));
        }
        return bodyGroupsList;
    }
}