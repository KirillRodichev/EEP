package model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "gym")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class FakeGym {
    private Equipment equipment;

    @XmlElementRef(name = "parent_equipment")
    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
