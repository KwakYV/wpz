package ru.wpz.lessons.lesson1.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "device", schema = "wpz")
public class EdgeDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DEVICE_NUMBER")
    private Long number;

    @Builder
    public EdgeDevice(Long id, Long number){
        this.id = id;
        this.number = number;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
