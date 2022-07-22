package ru.wpz.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema = "wpz",name="report")
@Data
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organizationId;

    @ManyToOne
    @JoinColumn(name = "obj_id")
    private Building building;

    @ManyToOne
    @JoinColumn(name = "zoneNumber")
    private Parking zoneId;

    @ManyToOne
    @JoinColumn(name = "dev_Id")
    private Device device;

    @Column(name = "status")
    private Integer status;

    @Column(name = "time_message")
    private LocalDateTime timeMessage;
}
