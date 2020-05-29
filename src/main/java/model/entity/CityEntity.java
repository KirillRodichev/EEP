package model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "CITIES")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "cities_seq_gen")
    @SequenceGenerator(name = "cities_seq_gen", sequenceName = "cities_seq", allocationSize = 1)
    @Column(name = "CITY_ID")
    private int id;

    @Column(name = "CITY_NAME")
    private String name;

    @OneToMany(mappedBy = "gymCity", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<GymEntity> gyms;

    @OneToMany(mappedBy = "userCity", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<UserEntity> users;
}
