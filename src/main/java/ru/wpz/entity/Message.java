package ru.wpz.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(schema = "wpz",name="message")
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id")
    private Device devId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_dt")
    private LocalDateTime createdDt;
}
