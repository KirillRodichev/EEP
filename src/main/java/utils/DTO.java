package utils;

import model.Equipment;
import model.EquipmentDTO;

import java.util.*;

public class DTO {
    public static List<EquipmentDTO> getDTO(List<Equipment> equipment, Map<Integer, List<String>> eqBodyGroups) {
        List<EquipmentDTO> DTOList = new ArrayList<>();
        for (Equipment eq : equipment) {
            int id = eq.getId();
            Set<String> bgSet = new HashSet<>(eqBodyGroups.get(id));
            DTOList.add(new EquipmentDTO(id, eq.getName(), eq.getDescription(), eq.getImgPath(), bgSet));
        }
        return DTOList;
    }
}
