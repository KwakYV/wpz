package ru.wpz.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "wpz",name="building")
@Data
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "obg_name")
    private String name;

    @Column(name = "desc")
    private String desc;

    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;

    @OneToMany(mappedBy = "id")
    private List<Parking> parking;
}
