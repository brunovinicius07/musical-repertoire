package com.music.services.impl;

import com.music.model.exceptions.exceptionHandler.AlertException;
import com.music.model.dto.request.ScheduleEventRequestDto;
import com.music.model.dto.response.ScheduleEventResponseDto;
import com.music.model.entity.ScheduleEvent;
import com.music.model.mapper.ScheduleEventMapper;
import com.music.repositories.ScheduleEventRepository;
import com.music.services.ScheduleEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ScheduleEventServiceImpl implements ScheduleEventService {

    private final ScheduleEventRepository scheduleEventRepository;

    private final ScheduleEventMapper scheduleEventMapper;

    @Override
    @Transactional(readOnly = false)
    public ScheduleEventResponseDto createEvent(ScheduleEventRequestDto scheduleEventRequestDto) {
        existingScheduleEvent(scheduleEventRequestDto.getIdUser() ,scheduleEventRequestDto.getDay(), scheduleEventRequestDto.getOpening());
        ScheduleEvent scheduleEvent = scheduleEventMapper.toScheduleEvent(scheduleEventRequestDto);

        return scheduleEventMapper.toScheduleEventResponseDto(scheduleEventRepository.save(scheduleEvent));
    }

    @Transactional(readOnly = true)
    public void existingScheduleEvent(Long idUser, LocalDate day, LocalTime opening){

        Optional<ScheduleEvent> scheduleEvent = scheduleEventRepository.findByUserIdUserAndDayAndOpening(idUser,day, opening);

        if(scheduleEvent.isPresent()){
            throw new AlertException(
                    "warn",
                    String.format("Já existe um evento para este dia e horaio", opening),
                    HttpStatus.CONFLICT
            );
        }
    }

    @Override
    @Transactional(readOnly = false)
    public ScheduleEventResponseDto updateScheduleEvent(Long idScheduleEvent, ScheduleEventRequestDto scheduleEventRequestDto) {
        ScheduleEvent scheduleEvent = validateScheduleEvent(idScheduleEvent);
        scheduleEvent.setDay(scheduleEventRequestDto.getDay());
        scheduleEvent.setOpening(scheduleEventRequestDto.getOpening());
        scheduleEvent.setClosure(scheduleEventRequestDto.getClosure());
        scheduleEvent.setTitle(scheduleEventRequestDto.getTitle());
        scheduleEvent.setDescription(scheduleEventRequestDto.getDescription());

        return scheduleEventMapper.toScheduleEventResponseDto(scheduleEventRepository.save(scheduleEvent));
    }

    @Transactional(readOnly = true)
    public ScheduleEvent validateScheduleEvent(Long idScheduleEvent) {
        Optional<ScheduleEvent> scheduleEvent = scheduleEventRepository.findById(idScheduleEvent);

        if (scheduleEvent.isEmpty()) {
            throw new AlertException(
                    "warn",
                    String.format("Evento com id %S não cadastrada!", idScheduleEvent),
                    HttpStatus.NOT_FOUND
            );
        }
        return scheduleEvent.get();
    }

    @Override
    public String deleteScheduleEvent(Long idScheduleEvent) {

        ScheduleEvent scheduleEvent = validateScheduleEvent(idScheduleEvent);
        scheduleEventRepository.delete(scheduleEvent);

        return "Evento com o id " + idScheduleEvent + " apagado com sucesso!";
    }
}
