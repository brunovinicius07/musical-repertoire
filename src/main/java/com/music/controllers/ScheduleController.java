package com.music.controllers;

import com.music.model.dto.request.ScheduleRequestDto;
import com.music.services.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(value = "v1/music/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping()
    public ResponseEntity<Object> createSchedule(@RequestBody @Valid ScheduleRequestDto scheduleRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(scheduleRequestDto));
    }

    @GetMapping()
    public ResponseEntity<Object> getAllEvent(){
        return ResponseEntity.ok(scheduleService.getAllEvent());
    }
}
