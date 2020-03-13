package model;

import lombok.Data;

@Data
public class Equipment {
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
