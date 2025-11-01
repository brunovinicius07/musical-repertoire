package com.music.services;

import com.music.model.dto.request.ScheduleRequestDto;
import com.music.model.dto.response.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {
    ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto);

    List<ScheduleResponseDto> getAllEvent();

    ScheduleResponseDto getScheduleByIdSchedule(Long idSchedule);
}
