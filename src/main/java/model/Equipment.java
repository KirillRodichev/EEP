package model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Equipment implements Serializable {
    private int id;
    private String name;
    private String description;
    private String imgPath;
}
