package utils;

import model.Equipment;
import model.EquipmentDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DTO {
    public static List<EquipmentDTO> getDTO(List<Equipment> equipment, Map<Integer, Set<String>> eqBodyGroups) {
        List<EquipmentDTO> DTOList = new ArrayList<>();
        for (Equipment eq : equipment) {
            int id = eq.getId();
            Set<String> bodyGroups = eqBodyGroups.get(id);
            DTOList.add(new EquipmentDTO(id, eq.getName(), eq.getDescription(), eq.getImgPath(), bodyGroups));
        }
        return DTOList;
    }
}
