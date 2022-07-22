package ru.wpz.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="report_day")
@Data
@Builder
public class ReportMoment {

    @Column(name = "dev_number")
    private Integer devNumber;

    @Column(name = "max_dt")
    private LocalDateTime maxDt;

    @Column(name = "status")
    private Integer status;
}
