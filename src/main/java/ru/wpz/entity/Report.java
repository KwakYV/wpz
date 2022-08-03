package ru.wpz.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema = "wpz",name="report")
@Data
@Builder
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "organization_id")
    private Long organizationId;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "building_id")
    private Long buildingId;

    @Column(name = "building_name")
    private String buildingName;

    @Column(name = "zone_number")
    private Integer zoneNumber;

    @Column(name = "device_id")
    private Long deviceId;

    @Column(name = "device_number")
    private Integer deviceNumber;

    @Column(name = "status")
    private Integer status;

    @Column(name = "message_dt")
    private LocalDateTime messageDt;
}
