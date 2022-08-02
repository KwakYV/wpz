package ru.wpz.entity;

import io.swagger.annotations.ApiModel;
import jdk.jfr.Timestamp;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(schema = "wpz",name="message")
@Data
@ApiModel
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "dev_id")
    private Long devId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_dt")
    private LocalDateTime createdDt;
}
