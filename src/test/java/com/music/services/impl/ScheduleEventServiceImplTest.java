package com.music.services.impl;

import com.music.model.dto.request.ScheduleEventRequestDto;
import com.music.model.dto.response.ScheduleEventResponseDto;
import com.music.model.entity.ScheduleEvent;
import com.music.model.exceptions.dayAndHorException.DayFinishEventException;
import com.music.model.exceptions.dayAndHorException.DayToStartEventException;
import com.music.model.exceptions.dayAndHorException.HoursAfterException;
import com.music.model.exceptions.schedule.ScheduleEventIsPresentException;
import com.music.model.exceptions.schedule.ScheduleEventNotFoundException;
import com.music.model.mapper.ScheduleEventMapper;
import com.music.repositories.ScheduleEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScheduleEventServiceImplTest {

    @Mock
    private ScheduleEventRepository scheduleEventRepository;

    @Mock
    private ScheduleEventMapper scheduleEventMapper;

    @InjectMocks
    private ScheduleEventServiceImpl scheduleEventService;

    private ScheduleEventRequestDto requestDto;
    private ScheduleEvent scheduleEvent;
    private ScheduleEventResponseDto responseDto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        scheduleEventService = Mockito.spy(scheduleEventService);

        LocalDate date = LocalDate.of(2025, 11, 7);
        LocalDateTime opening = date.atTime(10, 0);
        LocalDateTime closure = date.atTime(12, 0);

        requestDto = new ScheduleEventRequestDto(1L, date, opening, closure, "Evento Teste",
                "Descrição");
        scheduleEvent = ScheduleEvent.builder()
                .idScheduleEvent(1L)
                .day(date)
                .opening(opening)
                .closure(closure)
                .title("Evento Teste")
                .description("Descrição")
                .build();

        responseDto = new ScheduleEventResponseDto(1L, date, opening, closure, "Evento Teste",
                "Descrição");
    }

    @Test
    void shouldCreateEventSuccessfully() {
        when(scheduleEventRepository.findByUserIdUserAndDayAndOpening(anyLong(), any(), any()))
                .thenReturn(Optional.empty());
        when(scheduleEventMapper.toScheduleEvent(requestDto)).thenReturn(scheduleEvent);
        when(scheduleEventRepository.save(scheduleEvent)).thenReturn(scheduleEvent);
        when(scheduleEventMapper.toScheduleEventResponseDto(scheduleEvent)).thenReturn(responseDto);

        ScheduleEventResponseDto result = scheduleEventService.createEvent(requestDto);

        assertNotNull(result);
        assertEquals("Evento Teste", result.getTitle());
        verify(scheduleEventRepository).save(scheduleEvent);
    }

    @Test
    void shouldThrowException_WhenEventAlreadyExists() {
        when(scheduleEventRepository.findByUserIdUserAndDayAndOpening(anyLong(), any(), any()))
                .thenReturn(Optional.of(scheduleEvent));

        assertThrows(ScheduleEventIsPresentException.class,
                () -> scheduleEventService.existingScheduleEvent(1L, requestDto.getDay(),
                        requestDto.getOpening()));
    }

    @Test
    void shouldThrowException_WhenOpeningDayIsDifferentFromSelectedDay() {
        ScheduleEventRequestDto invalid = new ScheduleEventRequestDto(
                1L,
                LocalDate.of(2025, 11, 7),
                LocalDateTime.of(2025, 11, 8, 10, 0),
                LocalDateTime.of(2025, 11, 8, 12, 0),
                "Evento inválido",
                "Descrição"
        );

        assertThrows(DayToStartEventException.class,
                () -> scheduleEventService.ValidateDayAndTimeOfEvent(invalid));
    }

    @Test
    void shouldThrowException_WhenClosureIsNotSameOrNextDay() {
        ScheduleEventRequestDto invalid = new ScheduleEventRequestDto(
                1L,
                LocalDate.of(2025, 11, 7),
                LocalDateTime.of(2025, 11, 7, 10, 0),
                LocalDateTime.of(2025, 11, 9, 12, 0),
                "Evento inválido",
                "Descrição"
        );

        assertThrows(DayFinishEventException.class,
                () -> scheduleEventService.ValidateDayAndTimeOfEvent(invalid));
    }

    @Test
    void shouldNotThrowException_WhenClosureIsSameOrNextDay() {
        ScheduleEventRequestDto sameDay = new ScheduleEventRequestDto(
                1L,
                LocalDate.of(2025, 11, 7),
                LocalDateTime.of(2025, 11, 7, 10, 0),
                LocalDateTime.of(2025, 11, 7, 12, 0),
                "Evento válido - mesmo dia",
                "Fechamento no mesmo dia"
        );

        ScheduleEventRequestDto nextDay = new ScheduleEventRequestDto(
                1L,
                LocalDate.of(2025, 11, 7),
                LocalDateTime.of(2025, 11, 7, 22, 0),
                LocalDateTime.of(2025, 11, 8, 2, 0),
                "Evento válido - dia seguinte",
                "Fechamento no dia seguinte"
        );

        assertDoesNotThrow(() -> scheduleEventService.ValidateDayAndTimeOfEvent(sameDay));
        assertDoesNotThrow(() -> scheduleEventService.ValidateDayAndTimeOfEvent(nextDay));
    }

    @Test
    void shouldThrowException_WhenOpeningIsAfterClosure() {
        ScheduleEventRequestDto invalid = new ScheduleEventRequestDto(
                1L,
                LocalDate.of(2025, 11, 7),
                LocalDateTime.of(2025, 11, 7, 13, 0),
                LocalDateTime.of(2025, 11, 7, 10, 0),
                "Evento inválido",
                "Descrição"
        );

        assertThrows(HoursAfterException.class,
                () -> scheduleEventService.ValidateDayAndTimeOfEvent(invalid));
    }

    @Test
    void shouldThrowException_WhenOpeningIsEqualToClosure() {
        ScheduleEventRequestDto invalid = new ScheduleEventRequestDto(
                1L,
                LocalDate.of(2025, 11, 7),
                LocalDateTime.of(2025, 11, 7, 10, 0),
                LocalDateTime.of(2025, 11, 7, 10, 0),
                "Evento inválido",
                "Horário inicial igual ao final"
        );

        assertThrows(HoursAfterException.class,
                () -> scheduleEventService.ValidateDayAndTimeOfEvent(invalid));
    }

    @Test
    void shouldValidateEventSuccessfully() {
        assertDoesNotThrow(() -> scheduleEventService.ValidateDayAndTimeOfEvent(requestDto));
    }

    @Test
    void shouldReturnAllEventsSuccessfully() {
        when(scheduleEventRepository.findScheduleEventsByUserIdUser(1L)).thenReturn(List.of(scheduleEvent));
        when(scheduleEventMapper.toScheduleEventsResponse(anyList())).thenReturn(List.of(responseDto));

        List<ScheduleEventResponseDto> result = scheduleEventService.getAllScheduleEventByIdUser(1L);

        assertEquals(1, result.size());
        assertEquals("Evento Teste", result.get(0).getTitle());
    }

    @Test
    void shouldThrowException_WhenUserHasNoEvents() {
        when(scheduleEventRepository.findScheduleEventsByUserIdUser(1L)).thenReturn(Collections.emptyList());

        assertThrows(ScheduleEventNotFoundException.class,
                () -> scheduleEventService.getAllScheduleEventByIdUser(1L));
    }

    @Test
    void shouldUpdateScheduleEvent_WhenAllFieldsAreNotNull() {
        when(scheduleEventRepository.findById(1L)).thenReturn(Optional.of(scheduleEvent));
        when(scheduleEventRepository.save(scheduleEvent)).thenReturn(scheduleEvent);
        when(scheduleEventMapper.toScheduleEventResponseDto(scheduleEvent)).thenReturn(responseDto);

        ScheduleEventRequestDto updateDto = new ScheduleEventRequestDto(
                1L,
                LocalDate.of(2025, 11, 7),
                LocalDateTime.of(2025, 11, 7, 8, 0),
                LocalDateTime.of(2025, 11, 7, 10, 0),
                "Novo Evento",
                "Nova Descrição"
        );

        doNothing().when(scheduleEventService).ValidateDayAndTimeOfEvent(any());

        ScheduleEventResponseDto result = scheduleEventService.updateScheduleEvent(1L, updateDto);

        assertNotNull(result);
        assertEquals("Novo Evento", scheduleEvent.getTitle());
        verify(scheduleEventService, times(1)).ValidateDayAndTimeOfEvent(eq(updateDto));
        verify(scheduleEventRepository).save(scheduleEvent);
    }

    @Test
    void shouldUpdateScheduleEvent_WhenDayIsNull() {
        when(scheduleEventRepository.findById(1L)).thenReturn(Optional.of(scheduleEvent));
        when(scheduleEventRepository.save(scheduleEvent)).thenReturn(scheduleEvent);
        when(scheduleEventMapper.toScheduleEventResponseDto(scheduleEvent)).thenReturn(responseDto);

        ScheduleEventRequestDto updateDto = new ScheduleEventRequestDto(
                1L,
                null,
                scheduleEvent.getOpening(),
                scheduleEvent.getClosure(),
                "Título com dia nulo",
                "Descrição atualizada"
        );

        doNothing().when(scheduleEventService).ValidateDayAndTimeOfEvent(any());

        ScheduleEventResponseDto result = scheduleEventService.updateScheduleEvent(1L, updateDto);

        assertNotNull(result);
        assertEquals("Título com dia nulo", scheduleEvent.getTitle());
        assertEquals("Descrição atualizada", scheduleEvent.getDescription());
        verify(scheduleEventRepository).save(scheduleEvent);
    }

    @Test
    void shouldUpdateScheduleEvent_WhenTitleIsNull() {
        when(scheduleEventRepository.findById(1L)).thenReturn(Optional.of(scheduleEvent));
        when(scheduleEventRepository.save(scheduleEvent)).thenReturn(scheduleEvent);
        when(scheduleEventMapper.toScheduleEventResponseDto(scheduleEvent)).thenReturn(responseDto);

        ScheduleEventRequestDto updateDto = new ScheduleEventRequestDto(
                1L,
                scheduleEvent.getDay(),
                scheduleEvent.getOpening(),
                scheduleEvent.getClosure(),
                null,
                "Descrição alterada"
        );

        doNothing().when(scheduleEventService).ValidateDayAndTimeOfEvent(any());

        ScheduleEventResponseDto result = scheduleEventService.updateScheduleEvent(1L, updateDto);

        assertNotNull(result);
        assertEquals("Evento Teste", scheduleEvent.getTitle());
        assertEquals("Descrição alterada", scheduleEvent.getDescription());
        verify(scheduleEventRepository).save(scheduleEvent);
    }

    @Test
    void shouldNotCallValidate_WhenOpeningIsNull() {
        when(scheduleEventRepository.findById(1L)).thenReturn(Optional.of(scheduleEvent));
        when(scheduleEventRepository.save(scheduleEvent)).thenReturn(scheduleEvent);
        when(scheduleEventMapper.toScheduleEventResponseDto(scheduleEvent)).thenReturn(responseDto);

        doNothing().when(scheduleEventService).ValidateDayAndTimeOfEvent(any());

        ScheduleEventRequestDto updateDto = new ScheduleEventRequestDto(
                1L,
                LocalDate.of(2025, 11, 7),
                null,
                LocalDateTime.of(2025, 11, 7, 12, 0),
                "Título",
                "Descrição"
        );

        scheduleEventService.updateScheduleEvent(1L, updateDto);
        verify(scheduleEventService, never()).ValidateDayAndTimeOfEvent(any());
    }

    @Test
    void shouldNotCallValidate_WhenClosureIsNull() {
        when(scheduleEventRepository.findById(1L)).thenReturn(Optional.of(scheduleEvent));
        when(scheduleEventRepository.save(scheduleEvent)).thenReturn(scheduleEvent);
        when(scheduleEventMapper.toScheduleEventResponseDto(scheduleEvent)).thenReturn(responseDto);

        doNothing().when(scheduleEventService).ValidateDayAndTimeOfEvent(any());

        ScheduleEventRequestDto updateDto = new ScheduleEventRequestDto(
                1L,
                LocalDate.of(2025, 11, 7),
                LocalDateTime.of(2025, 11, 7, 9, 0),
                null,
                "Título",
                "Descrição"
        );

        scheduleEventService.updateScheduleEvent(1L, updateDto);
        verify(scheduleEventService, never()).ValidateDayAndTimeOfEvent(any());
    }

    @Test
    void shouldDeleteEventSuccessfully() {
        when(scheduleEventRepository.findById(1L)).thenReturn(Optional.of(scheduleEvent));

        String result = scheduleEventService.deleteScheduleEvent(1L);

        assertTrue(result.contains("apagado com sucesso"));
        verify(scheduleEventRepository).delete(scheduleEvent);
    }

    @Test
    void shouldValidateScheduleEventSuccessfully() {
        when(scheduleEventRepository.findById(1L)).thenReturn(Optional.of(scheduleEvent));
        ScheduleEvent result = scheduleEventService.validateScheduleEvent(1L);

        assertEquals(scheduleEvent, result);
    }

    @Test
    void shouldThrowException_WhenScheduleEventNotFound() {
        when(scheduleEventRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ScheduleEventIsPresentException.class,
                () -> scheduleEventService.validateScheduleEvent(1L));
    }
}
