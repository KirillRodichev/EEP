package model.entity;

import lombok.*;
import model.Equipment;
import model.entity.interfaces.Accessible;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@Entity
@Table(name = "EQUIPMENT")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class EquipmentEntity extends Equipment implements Accessible, Serializable {
    private List<GymEntity> eqGyms;
    private List<BodyGroupEntity> eqBodyGroups;

    public EquipmentEntity(
            int id,
            String name,
            String description,
            String imgPath,
            List<GymEntity> eqGyms,
            List<BodyGroupEntity> eqBodyGroups
    ) {
        super(id, name, description, imgPath);
        if (eqGyms != null) this.eqGyms = new ArrayList<>(eqGyms);
        if  (eqBodyGroups != null) this.eqBodyGroups = new ArrayList<>(eqBodyGroups);
    }

    @Id
    @Column(name = "EQUIPMENT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "equipment_seq_gen")
    @SequenceGenerator(name = "equipment_seq_gen", sequenceName = "equipment_seq", allocationSize = 1)
    @Override
    public int getId() {
        return super.getId();
    }

    @Column(name = "EQUIPMENT_NAME")
    @Override
    public String getName() {
        return super.getName();
    }

    @Column(name = "EQUIPMENT_DESCRIPTION")
    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Column(name = "EQUIPMENT_IMG_PATH")
    @Override
    public String getImgPath() {
        return super.getImgPath();
    }

    @ManyToMany(fetch = FetchType.LAZY/*, cascade = CascadeType.ALL*/)
    @JoinTable(
            name = "GYMS_EQUIPMENT",
            joinColumns = @JoinColumn(name = "EQUIPMENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "GYM_ID")
    )
    public List<GymEntity> getEqGyms() { return eqGyms; }

    public void setEqGyms(List<GymEntity> eqGyms) { this.eqGyms = eqGyms; }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "B_GROUPS_EQUIPMENT",
            joinColumns = @JoinColumn(name = "EQUIPMENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "B_GROUP_ID")
    )
    public List<BodyGroupEntity> getEqBodyGroups() { return eqBodyGroups; }
}
