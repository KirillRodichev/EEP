package model.entity;

import lombok.*;
import model.Equipment;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@Entity
@Table(name = "EQUIPMENT")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class EquipmentEntity extends Equipment {
    private List<GymEntity> gyms;
    private List<BodyGroupEntity> bodyGroups;

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "GYMS_EQUIPMENT",
            joinColumns = @JoinColumn(name = "EQUIPMENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "GYM_ID")
    )
    public List<GymEntity> getGyms() { return gyms; }

    public void setGyms(List<GymEntity> gyms) { this.gyms = gyms; }

    @ManyToMany(mappedBy = "equipment", fetch = FetchType.LAZY)
    public List<BodyGroupEntity> getBodyGroups() { return bodyGroups; }
}
