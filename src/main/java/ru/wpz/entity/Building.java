package ru.wpz.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "wpz",name="building")
@Data
@ApiModel
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "obj_name")
    private String name;

    @Column(name = "obj_desc")
    private String desc;


    @Column(name = "org_id")
    private Long orgId;

}
