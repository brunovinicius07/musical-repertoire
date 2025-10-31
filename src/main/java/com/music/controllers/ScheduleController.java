package com.music.controllers;

import com.music.model.dto.request.ScheduleRequestDto;
import com.music.model.dto.response.ScheduleResponseDto;
import com.music.services.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@RestController()
@RequestMapping(value = "v1/music/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping()
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @RequestBody @Valid ScheduleRequestDto scheduleRequestDto){
        var scheduleResponse = scheduleService.createSchedule(scheduleRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleResponse);
    }

    @GetMapping()
    public ResponseEntity<List<ScheduleResponseDto>> getAllEvent(){
        var scheduleResponse = scheduleService.getAllEvent();
        return ResponseEntity.ok(scheduleResponse);
    }
}
