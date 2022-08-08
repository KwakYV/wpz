package ru.wpz.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeviceBusyDto {
    private Long deviceId;
    private Float busyPercent;
}
