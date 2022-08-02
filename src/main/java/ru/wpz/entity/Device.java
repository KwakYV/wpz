package ru.wpz.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "wpz",name="device")
@Data
@ApiModel
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "dev_number")
    private Integer devNumber;

    @Column(name = "zone_id")
    private Long zoneId;

    @Column(name="x_coord")
    private int xCor;

    @Column(name="y_coord")
    private int yCor;


}
