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
@XmlType(propOrder = {"id", "name", "logoPath", "websiteURL", "website", "phone", "address"})
public class Gym implements Serializable {
    private int id;
    private String name;
    private String logoPath;
    private String websiteURL;
    private String website;
    private String phone;
    private String address;

    @XmlAttribute
    public int getId() { return this.id; }

    @XmlElement(name = "logo_path")
    public String getLogoPath() { return this.logoPath; }

    @XmlElement(name = "website_URL")
    public String getWebsiteURL() { return this.websiteURL; }
}
