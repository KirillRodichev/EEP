package model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class User implements Serializable {
    private int id;
    private String name;
    private String email;
    private String password;
    private String mode;
}
