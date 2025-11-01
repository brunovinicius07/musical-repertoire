package com.music.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleEventRequestDto {

    private Long idSchedule;

    private Long idUser;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate day;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime opening;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime closure;

    private String title;

    private String description;
}
