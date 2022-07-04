package ru.wpz.lessons.lesson1.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="device")
@Data
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
