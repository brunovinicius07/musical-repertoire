package com.music.services.impl;

import com.music.exception.AlertException;
import com.music.model.dto.request.ScheduleEventRequestDto;
import com.music.model.dto.response.ScheduleEventResponseDto;
import com.music.model.entity.Schedule;
import com.music.model.entity.ScheduleEvent;
import com.music.model.mapper.ScheduleEventMapper;
import com.music.repositories.ScheduleEventRepository;
import com.music.services.ScheduleEventService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class ScheduleEventServiceImpl implements ScheduleEventService {

    private final ScheduleEventRepository scheduleEventRepository;

    private final ScheduleServiceImpl scheduleService;

    private final ScheduleEventMapper scheduleEventMapper;

    public ScheduleEventServiceImpl(ScheduleEventRepository scheduleEventRepository, ScheduleServiceImpl scheduleService, ScheduleEventMapper scheduleEventMapper) {
        this.scheduleEventRepository = scheduleEventRepository;
        this.scheduleService = scheduleService;
        this.scheduleEventMapper = scheduleEventMapper;
    }


    @Override
    @Transactional(readOnly = false)
    public ScheduleEventResponseDto registerEvent(ScheduleEventRequestDto scheduleEventRequestDto) {

        existingScheduleEvent(scheduleEventRequestDto.getCdUser() ,scheduleEventRequestDto.getDay(), scheduleEventRequestDto.getOpening());

        Schedule schedule = scheduleService.validateSchedule(scheduleEventRequestDto.getCdSchedule());

        ScheduleEvent scheduleEvent = scheduleEventMapper.toScheduleEvent(scheduleEventRequestDto);
        scheduleEvent.setSchedule(schedule);

        return scheduleEventMapper.toScheduleEventResponseDto(scheduleEventRepository.save(scheduleEvent));
    }

    @Transactional(readOnly = true)
    public void existingScheduleEvent(Long cdUser, LocalDate day, LocalTime opening){

        Optional<ScheduleEvent> scheduleEvent = scheduleEventRepository.findByUserCdUserAndDayAndOpening(cdUser,day, opening);

        if(scheduleEvent.isPresent()){
            throw new AlertException(
                    "warn",
                    String.format("Já existe um evento para este dia e horaio", opening),
                    HttpStatus.CONFLICT
            );
        }
    }
}
