package xml;

import model.Equipment;
import model.EquipmentDTO;
import model.FakeGym;
import model.GymDTO;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.*;

public class EntryPoint {
    public static void main(String[] args) {
        GymSSB gSSB = new GymSSB();

        Set<String> bodyGroups = new HashSet<>();
        bodyGroups.add("chest");
        bodyGroups.add("back");

        Set<String> bodyGroups1 = new HashSet<>();
        bodyGroups1.add("Calves");
        bodyGroups1.add("Biceps");

        EquipmentDTO equipmentDTO = new EquipmentDTO(2, "super Power", "description", "image", bodyGroups);
        EquipmentDTO equipmentDTO1 = new EquipmentDTO(2, "extra destroyer", "description", "img", bodyGroups1);

        List<Equipment> allEq = new ArrayList<>();
        allEq.add(equipmentDTO);
        allEq.add(equipmentDTO1);

        GymDTO gymDTO = new GymDTO(1, "name", "img path", "web URL", "web", "89272095064", "Molodogvardeyskaya 178", allEq);

        try {
            gSSB.exportXML(gymDTO);
        } catch (FileNotFoundException | JAXBException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
