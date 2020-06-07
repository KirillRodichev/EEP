package model.entity;

import lombok.*;
import model.entity.interfaces.Accessible;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "BODY_GROUPS")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class BodyGroupEntity implements Accessible, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "b_group_seq_gen")
    @SequenceGenerator(name = "b_group_seq_gen", sequenceName = "b_group_seq", allocationSize = 1)
    @Column(name = "B_GROUP_ID")
    private int id;

    @Column(name = "B_GROUP_NAME")
    private String name;

    @ManyToMany(mappedBy = "eqBodyGroups", fetch = FetchType.LAZY)
    private List<EquipmentEntity> bgEquipment;
}
