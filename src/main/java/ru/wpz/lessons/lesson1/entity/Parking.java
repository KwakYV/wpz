package ru.wpz.lessons.lesson1.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="parking")
@Data
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "schema_path")
    private String schemaPath;

    @Column(name = "zone_number")
    private Integer zoneNumber;

    @ManyToOne
    @JoinColumn(name = "obj_id")
    private Building building;
}
