package com.music.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleEventResponseDto {

    private long idScheduleEvent;

    private LocalDate day;

    private LocalDateTime opening;

    private LocalDateTime closure;

    private String title;

    private String description;
}
