package ru.wpz.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeviceStatus {
    private Long id;
    private Long deviceNumber;
    private int status;
    private int xCor;
    private int yCor;
}
