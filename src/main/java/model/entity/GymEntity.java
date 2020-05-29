package model.entity;

import lombok.*;
import model.Gym;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@Entity
@Table(name = "GYMS")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class GymEntity extends Gym {
    private Set<UserEntity> users;
    private CityEntity gymCity;
    private Set<EquipmentEntity> equipment;

    @Id
    @Column(name = "GYM_ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "gyms_seq_gen")
    @SequenceGenerator(name = "gyms_seq_gen", sequenceName = "gyms_seq", allocationSize = 1)
    @Override
    public int getId() {
        return super.getId();
    }

    @Column(name = "GYM_NAME")
    @Override
    public String getName() {
        return super.getName();
    }

    @Column(name = "GYM_LOGO_PATH")
    @Override
    public String getLogoPath() {
        return super.getLogoPath();
    }

    @Column(name = "GYM_WEBSITE_URL")
    @Override
    public String getWebsiteURL() {
        return super.getWebsiteURL();
    }

    @Column(name = "GYM_WEBSITE")
    @Override
    public String getWebsite() {
        return super.getWebsite();
    }

    @Column(name = "GYM_PHONE")
    @Override
    public String getPhone() {
        return super.getPhone();
    }

    @Column(name = "GYM_ADDRESS")
    @Override
    public String getAddress() {
        return super.getAddress();
    }

    @OneToMany(mappedBy = "gym", fetch = FetchType.LAZY, orphanRemoval = true)
    public Set<UserEntity> getUsers() { return users; }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "GYMS_CITIES",
            joinColumns = @JoinColumn(name = "GYM_ID"),
            inverseJoinColumns = @JoinColumn(name = "CITY_ID")
    )
    public CityEntity getGymCity() { return gymCity; }

    public void setGymCity(CityEntity city) { gymCity = city; }

    @ManyToMany(mappedBy = "gyms", fetch = FetchType.LAZY)
    public Set<EquipmentEntity> getEquipment() { return equipment; }
}
