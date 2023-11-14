package com.music.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleEventResponseDto {

    private long cdScheduleEvent;

    private Long cdSchedule;

    private LocalDate day;

    private LocalTime opening;

    private LocalTime closure;

    private String title;

    private String description;
}
