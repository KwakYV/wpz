package ru.wpz.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(schema = "wpz",name="parking")
@Data
@ApiModel
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "schema_path")
    private String schemaPath;

    @Column(name = "zone_number")
    private Integer zoneNumber;

    @Column(name = "obj_id")
    private Long building;
}
