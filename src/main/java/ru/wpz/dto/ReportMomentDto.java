package ru.wpz.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportMomentDto {

    private Integer total;
    private Integer statusTaken;
    private Integer statusFree;
    private Float percent;
}
