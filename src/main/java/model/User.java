package model;

import lombok.Data;

@Data
public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String mode;

    public User(int id, String name, String email, String password, String mode) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.mode = mode;
    }
}
