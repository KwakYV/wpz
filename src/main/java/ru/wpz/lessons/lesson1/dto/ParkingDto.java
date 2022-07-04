package ru.wpz.lessons.lesson1.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParkingDto {

    private Long id;
    private String schemaPath;
    private Integer zoneNumber;

}
