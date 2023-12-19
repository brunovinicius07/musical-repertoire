package com.music.services.impl;

import com.music.authentication.auth.AuthenticationService;
import com.music.authentication.config.exceptionHandler.AlertException;
import com.music.model.dto.request.ScheduleRequestDto;
import com.music.model.dto.response.ScheduleResponseDto;
import com.music.model.entity.Schedule;
import com.music.model.entity.User;
import com.music.model.mapper.ScheduleMapper;
import com.music.repositories.ScheduleRepository;
import com.music.services.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    private final AuthenticationService authenticationService;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, ScheduleMapper scheduleMapper, AuthenticationService authenticationService) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleMapper = scheduleMapper;
        this.authenticationService = authenticationService;
    }

    @Override
    @Transactional(readOnly = false)
    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {

        existingSchedule(scheduleRequestDto.getCdUser());
        User user = authenticationService.validateUser(scheduleRequestDto.getCdUser());

        Schedule schedule = scheduleMapper.toSchedule(scheduleRequestDto);
        schedule.setUser(user);

        return scheduleMapper.toScheduleResponseDto(scheduleRepository.save(schedule));
    }

    @Transactional(readOnly = true)
    public void existingSchedule(Long cdUser){

        Optional<Schedule> schedule = scheduleRepository.findById(cdUser);

        if(schedule.isPresent()){
            throw new AlertException(
                    "warn",
                    String.format("Já existe uma agenda para o usuário: %S", cdUser),
                    HttpStatus.CONFLICT
            );
        }
    }

    @Transactional(readOnly = true)
    public Schedule validateSchedule(Long cdSchedule) {
        Optional<Schedule> schedule = scheduleRepository.findById(cdSchedule);

        if (schedule.isEmpty()) {
            throw new AlertException(
                    "warn",
                    String.format("Nenhuma agenda encontrada!", cdSchedule),
                    HttpStatus.NOT_FOUND
            );
        }
        return schedule.get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> getAllEvent() {
        List<Schedule> scheduleList = validateListSchedule();

        return scheduleList.stream().map(scheduleMapper::toScheduleResponseDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleResponseDto getScheduleByCdSchedule(Long cdSchedule) {
        Schedule schedule = validateSchedule(cdSchedule);

        return scheduleMapper.toScheduleResponseDto(schedule);
    }

    @Transactional(readOnly = true)
    public List<Schedule> validateListSchedule() {
        List<Schedule> scheduleList = scheduleRepository.findAll();

        if (scheduleList.isEmpty()) {
            throw new AlertException(
                    "warn",
                    "Nenhum evento encontrado!",
                    HttpStatus.NOT_FOUND
            );
        }
        return scheduleList;
    }
}
