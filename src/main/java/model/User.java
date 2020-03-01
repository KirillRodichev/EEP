package model;

public class User {
    private static int id;
    private String name;
    private String email;
    private String password;
    private String city;
    private String gym;

    public User(String name, String email, String password, String city, String gym) {
        id++;
        this.name = name;
        this.email = email;
        this.password = password;
        this.city = city;
        this.gym = gym;
    }

    public static int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCity() {
        return city;
    }

    public String getGym() {
        return gym;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setGym(String gym) {
        this.gym = gym;
    }
}
