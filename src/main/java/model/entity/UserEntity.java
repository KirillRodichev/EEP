package model.entity;

import lombok.*;
import model.User;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@Entity
@Table(name = "USERS")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UserEntity extends User {
    private CityEntity userCity;
    private GymEntity gym;

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "users_seq_gen")
    @SequenceGenerator(name = "users_seq_gen", sequenceName = "users_seq", allocationSize = 1)
    @Override
    public int getId() {
        return super.getId();
    }

    @Column(name = "USER_NAME")
    @Override
    public String getName() {
        return super.getName();
    }

    @Column(name = "USER_EMAIL")
    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Column(name = "USER_PASSWORD")
    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Column(name = "USER_MODE")
    @Override
    public String getMode() {
        return super.getMode();
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "USERS_GYMS",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "GYM_ID")
    )
    public GymEntity getGym() { return gym; }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "USERS_CITIES",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "CITY_ID")
    )
    public CityEntity getUserCity() { return userCity; }
}
