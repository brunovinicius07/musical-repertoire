package com.music.services;

import com.music.model.dto.request.ScheduleEventRequestDto;
import com.music.model.dto.response.ScheduleEventResponseDto;

public interface ScheduleEventService {

    ScheduleEventResponseDto createEvent(ScheduleEventRequestDto scheduleEventRequestDto);

    ScheduleEventResponseDto updateScheduleEvent(Long cdScheduleEvent, ScheduleEventRequestDto scheduleEventRequestDto);

    String deleteScheduleEvent(Long idScheduleEvent);
}
