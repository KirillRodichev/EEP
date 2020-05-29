package model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "BODY_GROUPS")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class BodyGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "b_group_seq_gen")
    @SequenceGenerator(name = "b_group_seq_gen", sequenceName = "b_group_seq", allocationSize = 1)
    @Column(name = "B_GROUP_ID")
    private int id;

    @Column(name = "B_GROUP_NAME")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "B_GROUPS_EQUIPMENT",
            joinColumns = @JoinColumn(name = "B_GROUP_ID"),
            inverseJoinColumns = @JoinColumn(name = "EQUIPMENT_ID")
    )
    private Set<EquipmentEntity> equipment;
}
