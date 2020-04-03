package model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Gym implements Serializable {
    private int id;
    private String name;
    private String logoPath;
    private String websiteURL;
    private String website;
    private String phone;
    private String address;
}
