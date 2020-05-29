package model;

import model.Equipment;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement(name = "equipment")
public class EquipmentDTO extends Equipment implements Serializable {
    private Set<String> bodyGroups;

    public EquipmentDTO() {
        super();
    }

    public EquipmentDTO(int id, String name, String description, String imgPath, Set<String> bodyGroups) {
        super(id, name, description, imgPath);
        if (bodyGroups != null) {
            this.bodyGroups = new HashSet<>(bodyGroups);
        } else {
            this.bodyGroups = new HashSet<>();
        }
    }

    @XmlElementWrapper(name = "body_groups")
    @XmlElement(name = "body_group")
    public Set<String> getBodyGroups() {
        return bodyGroups;
    }

    public void setBodyGroups(Set<String> bodyGroups) {
        this.bodyGroups = bodyGroups;
    }
}
