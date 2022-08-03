package ru.wpz.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(schema = "wpz", name="organization")
@Data
@ApiModel
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "org_name")
    private String orgName;

    @Column(name = "org_desc")
    private String desc;

}
