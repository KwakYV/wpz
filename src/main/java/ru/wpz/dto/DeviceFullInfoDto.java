package ru.wpz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class DeviceFullInfoDto {
    private Long organizationId;
    private String organizationName;
    private Long buildingId;
    private String buildingName;
    private Integer zoneNumber;
    private Long deviceId;
    private Integer deviceNumber;
}
