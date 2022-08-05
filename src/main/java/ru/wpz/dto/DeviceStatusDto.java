package ru.wpz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor //Custom DTO - it is important to have all args constructor otherwise native query with custom mapping will not work.
public class DeviceStatusDto {
    private Long id;
    private Integer deviceNumber;
    private int status;
    private int xCor;
    private int yCor;
}
