package ru.wpz.lessons.lesson1.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeviceDto {

    private Long id;
    private Integer devNumber;
    private Long zoneId;
}
