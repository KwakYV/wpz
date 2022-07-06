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

    @ManyToOne
    @JoinColumn(name = "zoneNumber")
    private Parking zoneId;

    @OneToMany(mappedBy = "id")
    private List<Message> messages;
}