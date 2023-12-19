package com.music.services.impl;

import com.music.model.dto.request.ScheduleRequestDto;
import com.music.model.dto.response.ScheduleResponseDto;
import com.music.model.entity.Schedule;
import com.music.model.entity.ScheduleEvent;
import com.music.model.entity.User;
import com.music.model.mapper.ScheduleMapper;
import com.music.repositories.ScheduleRepository;
import com.music.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
class ScheduleServiceImplTest {

    public static final long CD_SCHEDULE = 1L;
    public static final long CD_USER = 1L;

    public static final List<ScheduleEvent> EVENTS = new ArrayList<>();

    @Autowired
    private  ScheduleServiceImpl scheduleService;

    @MockBean
    private ScheduleRequestDto scheduleRequestDto;

    @MockBean
    private ScheduleRepository scheduleRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ScheduleMapper scheduleMapper;

    @MockBean
    private Optional<User> optionalUser;

    private ScheduleResponseDto scheduleResponseDto;

    private Optional<Schedule> scheduleOptional;
    private Schedule schedule;

    private User user;

    @BeforeEach
    void setUp() {
        openMocks(this);
        startSchedule();
    }

    @Test
    void createSchedule() {
        when(userRepository.findById(anyLong())).thenReturn(optionalUser);
        when(scheduleMapper.toSchedule(any())).thenReturn(schedule);
        when(scheduleRepository.save(any())).thenReturn(schedule);
        when(scheduleMapper.toScheduleResponseDto(any())).thenReturn(scheduleResponseDto);

        ScheduleResponseDto response = scheduleService.createSchedule(scheduleRequestDto);

        verify(scheduleRepository, times(1)).save(any());
        verify(scheduleMapper, times(1)).toScheduleResponseDto(any());
        verify(scheduleMapper, times(1)).toScheduleResponseDto(any(Schedule.class));
        verify(scheduleMapper, times(1)).toSchedule(any());
        verify(scheduleMapper, times(1)).toScheduleResponseDto(any());
        verify(scheduleMapper).toScheduleResponseDto(schedule);
        verifyNoMoreInteractions(scheduleMapper);

        assertNotNull(response);
        assertEquals(scheduleResponseDto, response);
        assertEquals(CD_SCHEDULE, response.getCdSchedule());
        assertEquals(CD_USER, response.getCdUser());
        assertEquals(EVENTS, response.getEvents());
    }

    @Test
    void existingSchedule() {
        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(scheduleMapper.toScheduleResponseDto(any())).thenReturn(scheduleResponseDto);

        scheduleService.existingSchedule(CD_USER);

        verify(scheduleRepository, times(1)).findById(CD_USER);
        verifyNoMoreInteractions(scheduleRepository, scheduleMapper);
        verify(scheduleRepository, times(1)).findById(CD_USER);
        verify(scheduleMapper, never()).toScheduleResponseDto(any());
        verifyNoMoreInteractions(scheduleRepository);
        verifyNoMoreInteractions(scheduleMapper);
    }

    @Test
    void validateSchedule() {
        when(scheduleRepository.findById(anyLong())).thenReturn(scheduleOptional);
        when(scheduleMapper.toScheduleResponseDto(any())).thenReturn(scheduleResponseDto);
        when(scheduleMapper.toSchedule(any())).thenReturn(schedule);

        Schedule result = scheduleService.validateSchedule(CD_SCHEDULE);

        verify(scheduleRepository, times(1)).findById(CD_SCHEDULE);
        verifyNoMoreInteractions(scheduleRepository, scheduleMapper);
        verify(scheduleMapper, never()).toScheduleResponseDto(any());
        verify(scheduleRepository).findById(CD_SCHEDULE);

        assertNotNull(result);
        assertEquals(CD_SCHEDULE, result.getCdSchedule());
        assertEquals(user, result.getUser());
        assertEquals(EVENTS, result.getEvents());
        assertDoesNotThrow(() -> scheduleService.getScheduleByCdSchedule(CD_SCHEDULE));
        assertDoesNotThrow(() -> scheduleService.validateSchedule(CD_SCHEDULE));
    }

    @Test
    void getAllEvent() {
        when(scheduleRepository.findAll()).thenReturn(List.of(schedule));
        when(scheduleMapper.toScheduleResponseDto(any())).thenReturn(scheduleResponseDto);

        List<ScheduleResponseDto> response = scheduleService.getAllEvent();

        verify(scheduleRepository, times(1)).findAll();
        verify(scheduleMapper, times(1)).toScheduleResponseDto(any());
        verifyNoMoreInteractions(scheduleRepository, scheduleMapper);

        assertNotNull(response);
        assertEquals(CD_SCHEDULE, response.get(0).getCdSchedule());
        assertEquals(CD_USER, response.get(0).getCdUser());
        assertEquals(EVENTS, response.get(0).getEvents());
    }

    @Test
    void getScheduleByCdSchedule() {
    }

    @Test
    void validateListSchedule() {
    }

    private void startSchedule() {
        schedule = new Schedule(CD_SCHEDULE, user, EVENTS);
        scheduleResponseDto = new ScheduleResponseDto(CD_SCHEDULE, CD_USER, EVENTS);
        scheduleOptional = Optional.of(new Schedule(CD_SCHEDULE, user, EVENTS));
    }
}