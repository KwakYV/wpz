package ru.wpz.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@SqlResultSetMapping(
        name = "nativeSqlResult",
        entities = @EntityResult(entityClass = DeviceNumber.class)
)
public class DeviceNumber {

    @Id
    @Column(name = "dev_number")
    private Integer devNumber;
}
