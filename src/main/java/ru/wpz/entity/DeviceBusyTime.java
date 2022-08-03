package ru.wpz.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@SqlResultSetMapping(
        name = "nativeSqlResult",
        entities = @EntityResult(entityClass = DeviceBusyTime.class)
)
public class DeviceBusyTime {
    @Id
    @Column(name = "device")
    private Integer device;
    @Column(name = "sum")
    private Integer busy;
}
