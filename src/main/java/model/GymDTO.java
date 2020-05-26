package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "gym")
public class GymDTO extends Gym implements Serializable {
    private List<Equipment> equipment;

    public GymDTO() {
        super();
    }

    public GymDTO(
            int id,
            String name,
            String logoPath,
            String websiteURL,
            String website,
            String phone,
            String address,
            List<Equipment> equipment
    ) {
        super(id, name, logoPath, websiteURL, website, phone, address);
        this.equipment = new ArrayList<>(equipment);
    }

    @XmlElementWrapper(name = "all_equipment")
    @XmlElementRef(name = "parent_equipment")
    public List<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
    }
}
