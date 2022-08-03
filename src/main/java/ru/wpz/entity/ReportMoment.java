package ru.wpz.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@SqlResultSetMapping(
        name = "nativeSqlResult",
        entities = @EntityResult(entityClass = ReportMoment.class)
)
public class ReportMoment {

    @Id
    @Column(name = "total")
    private Integer total;

    @Column(name = "busy_count")
    private Integer busyCount;

    @Column(name = "free_count")
    private Integer freeCount;

    @Column(name = "percent")
    private Integer percent;

}
