package ru.wpz.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "wpz", name="organization")
@Data
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "org_name")
    private String orgName;

    @Column(name = "desc")
    private String desc;

    @OneToMany(mappedBy = "id")
    private List<Building> buildings;
}
