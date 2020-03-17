package model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Equipment implements Serializable {
    private int id;
    private String name;
    private String description;
    private String imgPath;

    public Equipment(int id, String name, String description, String imgPath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgPath = imgPath;
    }
}
