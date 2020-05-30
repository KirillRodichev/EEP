package utils;

import controllers.BodyGroupController;
import controllers.EquipmentController;
import model.Equipment;
import model.LoadedEquipment;

import java.sql.SQLException;
import java.util.*;

import static constants.Parameters.INITIAL_PAGE_NUMBER;
import static constants.Parameters.INITIAL_PAGE_SIZE;

public class DB {
    public static int loadData(
            List<Equipment> equipment, Map<Integer, Set<String>> map, List<Equipment> rest, int gymID,
            EquipmentController eqController, BodyGroupController bgController
    ) throws SQLException {
        List<Integer> eqIDs = eqController.getIDsByGymId(gymID);
        LoadedEquipment eqCountAndIDs = eqController.getIDsForSinglePage(INITIAL_PAGE_NUMBER, INITIAL_PAGE_SIZE, gymID);
        loadEquipment(eqController, eqCountAndIDs.getEquipmentIDsForSinglePage(), equipment);
        loadMap(equipment, map, bgController);
        loadRest(eqIDs, rest);
        return eqCountAndIDs.getEquipmentNumber();
    }

    public static int loadData(
            List<Equipment> equipment, Map<Integer, Set<String>> map, int gymID, int pageNumber, int pageSize, Set<Integer> filters
    ) throws SQLException {
        EquipmentController eqController = new EquipmentController();
        BodyGroupController bgController = new BodyGroupController();
        LoadedEquipment eqCountAndIDs = eqController.getIDsForSinglePage(pageNumber, pageSize, gymID, filters);
        loadEquipment(eqController, eqCountAndIDs.getEquipmentIDsForSinglePage(), equipment);
        loadMap(equipment, map, bgController);
        return eqCountAndIDs.getEquipmentNumber();
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
            List<Equipment> equipment, Map<Integer, Set<String>> map, BodyGroupController bodyGroupController
    ) throws SQLException {
        List<Set<Integer>> bodyGroupsIDsList = loadBodyGroupsIDs(bodyGroupController, equipment);
        List<Set<String>> bodyGroupsList = loadBodyGroups(bodyGroupController, bodyGroupsIDsList);
        for (int i = 0; i < equipment.size(); i++) {
            map.put(equipment.get(i).getId(), bodyGroupsList.get(i));
        }
    }

    private static List<Set<Integer>> loadBodyGroupsIDs(BodyGroupController controller, List<Equipment> equipment
    ) throws SQLException {
        List<Set<Integer>> IDsList = new ArrayList<>();
        for (Equipment eq : equipment) {
            Set<Integer> IDs = controller.getIdsListByEquipmentId(eq.getId());
            IDsList.add(IDs);
        }
        return IDsList;
    }

    private static List<Set<String>> loadBodyGroups(BodyGroupController controller, List<Set<Integer>> IDsList
    ) throws SQLException {
        List<Set<String>> bodyGroupsList = new ArrayList<>();
        for (Set<Integer> idList : IDsList) {
            Set<String> bodyGroups = new HashSet<>();
            for (Integer id : idList) {
                bodyGroups.add(controller.getById(id));
            }
            bodyGroupsList.add(new HashSet<>(bodyGroups));
        }
        return bodyGroupsList;
    }

    public static boolean ableToUpdate(String field) {
        if (field != null) {
            return field.length() != 0;
        }
        return false;
    }

    public static <T> boolean ableToUpdate(List<T> field) {
        if (field != null) {
            return field.size() != 0;
        }
        return false;
    }
}