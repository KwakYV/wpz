package ru.wpz.lessons.lesson1.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MessageDto {

    private Long id;
    private Long devId;
    private Integer status;
    private LocalDateTime createdDt;
}
