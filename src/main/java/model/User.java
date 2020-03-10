package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String name;
    private String email;
    private String password;
    private String mode;

    public User(String name, String email, String password, String mode) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.mode = mode;
    }
}
