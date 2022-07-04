package ru.wpz.lessons.lesson1.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrganizationDto {

    private Long id;
    private String orgName;
    private String desc;
}
