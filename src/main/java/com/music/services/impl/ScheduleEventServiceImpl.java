package com.music.services.impl;

import com.music.model.dto.request.ScheduleEventRequestDto;
import com.music.model.dto.response.ScheduleEventResponseDto;
import com.music.model.entity.ScheduleEvent;
import com.music.model.exceptions.DayAndHorException.DayFinishEventException;
import com.music.model.exceptions.DayAndHorException.DayToStartEventException;
import com.music.model.exceptions.DayAndHorException.HoursAfterException;
import com.music.model.exceptions.schedule.ScheduleEventIsPresentException;
import com.music.model.exceptions.schedule.ScheduleEventNotFoundException;
import com.music.model.mapper.ScheduleEventMapper;
import com.music.repositories.ScheduleEventRepository;
import com.music.services.ScheduleEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ScheduleEventServiceImpl implements ScheduleEventService {

    private final ScheduleEventRepository scheduleEventRepository;

    private final ScheduleEventMapper scheduleEventMapper;

    @Override
    @Transactional(readOnly = false)
    public ScheduleEventResponseDto createEvent(ScheduleEventRequestDto scheduleEventRequestDto) {
        ValidateDayAndTimeOfEvent(scheduleEventRequestDto);

        existingScheduleEvent(
                scheduleEventRequestDto.getIdUser(),
                scheduleEventRequestDto.getDay(),
                scheduleEventRequestDto.getOpening());

        ScheduleEvent scheduleEvent = scheduleEventMapper.toScheduleEvent(scheduleEventRequestDto);

        return scheduleEventMapper.toScheduleEventResponseDto(scheduleEventRepository.save(scheduleEvent));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleEventResponseDto> getAllScheduleEventByIdUser(Long idUser) {

        List<ScheduleEvent> scheduleEvents = scheduleEventRepository.findScheduleEventsByUserIdUser(idUser);
        if(scheduleEvents.isEmpty()){
            throw new ScheduleEventNotFoundException();
        }

        return scheduleEventMapper.toScheduleEventsResponse(scheduleEvents);
    }

    @Override
    @Transactional(readOnly = false)
    public ScheduleEventResponseDto updateScheduleEvent(Long idScheduleEvent,
                                                        ScheduleEventRequestDto scheduleEventRequestDto) {

        ScheduleEvent scheduleEvent = validateScheduleEvent(idScheduleEvent);

        if (scheduleEventRequestDto.getOpening() != null && scheduleEventRequestDto.getClosure() != null) {
            ValidateDayAndTimeOfEvent(scheduleEventRequestDto);
        }

        scheduleEvent.setDay(scheduleEventRequestDto.getDay() != null
                ? scheduleEventRequestDto.getDay() : scheduleEvent.getDay());
        scheduleEvent.setOpening(scheduleEventRequestDto.getOpening() != null
                ? scheduleEventRequestDto.getOpening() : scheduleEvent.getOpening());
        scheduleEvent.setClosure(scheduleEventRequestDto.getClosure() != null
                ? scheduleEventRequestDto.getClosure() : scheduleEvent.getClosure());
        scheduleEvent.setTitle(scheduleEventRequestDto.getTitle() != null
                ? scheduleEventRequestDto.getTitle() : scheduleEvent.getTitle());
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

    @Override
    @Transactional(readOnly = true)
    public void ValidateDayAndTimeOfEvent(ScheduleEventRequestDto scheduleEventRequestDto) {
        LocalDate selectedDay = scheduleEventRequestDto.getDay();
        LocalDateTime opening = scheduleEventRequestDto.getOpening();
        LocalDateTime closure = scheduleEventRequestDto.getClosure();

        if (!opening.toLocalDate().isEqual(selectedDay)) {
            throw new DayToStartEventException();
        }

        if (!(closure.toLocalDate().isEqual(selectedDay) || closure.toLocalDate()
                .isEqual(selectedDay.plusDays(1)))) {
            throw new DayFinishEventException();
        }

        if (opening.isAfter(closure) || opening.isEqual(closure)) {
            throw new HoursAfterException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void existingScheduleEvent(Long idUser, LocalDate day, LocalDateTime opening){

        scheduleEventRepository.findByUserIdUserAndDayAndOpening(idUser,day, opening)
                .ifPresent(s -> {
                    throw new ScheduleEventIsPresentException();
                });
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleEvent validateScheduleEvent(Long idScheduleEvent) {
        return scheduleEventRepository.findById(idScheduleEvent).orElseThrow(ScheduleEventIsPresentException::new);
    }
}
