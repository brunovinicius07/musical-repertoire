package com.music.services;

import com.music.model.dto.request.ScheduleEventRequestDto;
import com.music.model.dto.response.ScheduleEventResponseDto;

public interface ScheduleEventService {

    ScheduleEventResponseDto registerEvent(ScheduleEventRequestDto scheduleEventRequestDto);
}
