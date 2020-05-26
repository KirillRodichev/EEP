package model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@XmlType(propOrder = {"id", "name", "description", "imgPath"})
public class Equipment implements Serializable {
    private int id;
    private String name;
    private String description;
    private String imgPath;

    @XmlAttribute(name = "id")
    public int getId() { return this.id; }

    @XmlElement(name = "img_path")
    public String getImgPath() { return this.imgPath; }
}
