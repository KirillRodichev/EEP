package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Gym {
    private int id;
    private String name;
    private String logoPath;
    private String websiteURL;
    private String website;
    private String phone;
    private String address;

    public Gym(
            int id,
            String name,
            String website,
            String websiteURL,
            String logoPath,
            String phone,
            String address
    ) {
        this.id = id;
        this.name = name;
        this.logoPath = logoPath;
        this.websiteURL = websiteURL;
        this.website = website;
        this.phone = phone;
        this.address = address;
    }
}
