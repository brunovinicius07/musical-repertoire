package com.music.services;

import com.music.model.dto.request.ScheduleEventRequestDto;
import com.music.model.dto.response.ScheduleEventResponseDto;
import com.music.model.entity.ScheduleEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleEventService {

    ScheduleEventResponseDto createEvent(ScheduleEventRequestDto scheduleEventRequestDto);

    List<ScheduleEventResponseDto> getAllScheduleEventByIdUser(Long idUser);

    ScheduleEventResponseDto updateScheduleEvent(Long cdScheduleEvent, ScheduleEventRequestDto scheduleEventRequestDto);

    String deleteScheduleEvent(Long idScheduleEvent);

    void existingScheduleEvent(Long idUser, LocalDate day, LocalDateTime opening);

    ScheduleEvent validateScheduleEvent(Long idScheduleEvent);

    void ValidateDayAndTimeOfEvent(ScheduleEventRequestDto scheduleEventRequestDto);
}
