package ru.wpz.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeviceBusyTime {
    private Integer devId;
    private Integer busy;
}
