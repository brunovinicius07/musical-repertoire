package com.music.services.impl;

import com.music.model.dto.request.ScheduleEventRequestDto;
import com.music.model.dto.response.ScheduleEventResponseDto;
import com.music.model.entity.ScheduleEvent;
import com.music.model.exceptions.schedule.ScheduleEventIsPresentException;
import com.music.model.mapper.ScheduleEventMapper;
import com.music.repositories.ScheduleEventRepository;
import com.music.services.ScheduleEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@RequiredArgsConstructor
@Service
public class ScheduleEventServiceImpl implements ScheduleEventService {

    private final ScheduleEventRepository scheduleEventRepository;

    private final ScheduleEventMapper scheduleEventMapper;

    @Override
    @Transactional(readOnly = false)
    public ScheduleEventResponseDto createEvent(ScheduleEventRequestDto scheduleEventRequestDto) {
        existingScheduleEvent(scheduleEventRequestDto.getIdUser() ,scheduleEventRequestDto.getDay(),
                scheduleEventRequestDto.getOpening());
        ScheduleEvent scheduleEvent = scheduleEventMapper.toScheduleEvent(scheduleEventRequestDto);

        return scheduleEventMapper.toScheduleEventResponseDto(scheduleEventRepository.save(scheduleEvent));
    }

    @Override
    @Transactional(readOnly = false)
    public ScheduleEventResponseDto updateScheduleEvent(Long idScheduleEvent,
                                                        ScheduleEventRequestDto scheduleEventRequestDto) {
        ScheduleEvent scheduleEvent = validateScheduleEvent(idScheduleEvent);
        scheduleEvent.setDay(scheduleEventRequestDto.getDay());
        scheduleEvent.setOpening(scheduleEventRequestDto.getOpening());
        scheduleEvent.setClosure(scheduleEventRequestDto.getClosure());
        scheduleEvent.setTitle(scheduleEventRequestDto.getTitle());
        scheduleEvent.setDescription(scheduleEventRequestDto.getDescription());

        return scheduleEventMapper.toScheduleEventResponseDto(scheduleEventRepository.save(scheduleEvent));
    }

    @Override
    @Transactional(readOnly = false)
    public String deleteScheduleEvent(Long idScheduleEvent) {

        ScheduleEvent scheduleEvent = validateScheduleEvent(idScheduleEvent);
        scheduleEventRepository.delete(scheduleEvent);

        return "Evento com o id " + idScheduleEvent + " apagado com sucesso!";
    }

    @Transactional(readOnly = true)
    public void existingScheduleEvent(Long idUser, LocalDate day, LocalTime opening){

        scheduleEventRepository.findByUserIdUserAndDayAndOpening(idUser,day, opening)
                .ifPresent(s -> {
                    throw new ScheduleEventIsPresentException();
                });
    }

    @Transactional(readOnly = true)
    public ScheduleEvent validateScheduleEvent(Long idScheduleEvent) {
        return scheduleEventRepository.findById(idScheduleEvent).orElseThrow(ScheduleEventIsPresentException::new);
    }
}
