package model.entity;

import lombok.*;
import model.Gym;
import model.entity.interfaces.Accessible;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@Entity
@Table(name = "GYMS")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class GymEntity extends Gym implements Accessible, Serializable {
    private List<UserEntity> gymUsers;
    private CityEntity gymCity;
    private List<EquipmentEntity> gymEquipment;

    public GymEntity(
            int id,
            String name,
            String logoPath,
            String websiteURL,
            String website,
            String phone,
            String address,
            List<UserEntity> gymUsers,
            CityEntity gymCity,
            List<EquipmentEntity> gymEquipment
    ) {
        super(id, name, logoPath, websiteURL, website, phone, address);
        this.gymUsers = gymUsers == null ? new ArrayList<>() : gymUsers;
        this.gymCity = gymCity;
        this.gymEquipment = gymEquipment == null ? new ArrayList<>() : gymEquipment;
    }

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

    @OneToMany(mappedBy = "userGym", fetch = FetchType.LAZY, orphanRemoval = true)
    public List<UserEntity> getGymUsers() { return gymUsers; }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "GYMS_CITIES",
            joinColumns = @JoinColumn(name = "GYM_ID"),
            inverseJoinColumns = @JoinColumn(name = "CITY_ID")
    )
    public CityEntity getGymCity() { return gymCity; }

    public void setGymCity(CityEntity city) { gymCity = city; }

    @ManyToMany(mappedBy = "eqGyms", fetch = FetchType.LAZY)
    public List<EquipmentEntity> getGymEquipment() { return gymEquipment; }
}
