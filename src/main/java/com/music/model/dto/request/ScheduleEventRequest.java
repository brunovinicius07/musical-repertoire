package com.music.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleEventRequest {

    private Long userId;
    private LocalDate day;
    private LocalDateTime opening;
    private LocalDateTime closure;
    private String title;
    private String description;
}

