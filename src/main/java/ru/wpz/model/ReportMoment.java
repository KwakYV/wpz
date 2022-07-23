package ru.wpz.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReportMoment {
    private Integer devNumber;
    private LocalDateTime maxDt;
    private Integer status;
}
