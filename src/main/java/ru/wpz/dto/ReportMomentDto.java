package ru.wpz.dto;

import lombok.Data;

@Data
public class ReportMomentDto {

    private Integer total;
    private Integer statusTaken;
    private Integer statusFree;
    private Double percent;
}
