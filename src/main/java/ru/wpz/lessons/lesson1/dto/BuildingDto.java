package ru.wpz.lessons.lesson1.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BuildingDto {

    private Long id;
    private String name;
    private String desc;
}
