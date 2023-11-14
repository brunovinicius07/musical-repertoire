package com.music.controllers;

import com.music.model.dto.request.ScheduleEventRequestDto;
import com.music.services.ScheduleEventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(value = "v1/music/event")
public class ScheduleEventController {

    private final ScheduleEventService scheduleEventService;

    public ScheduleEventController(ScheduleEventService scheduleEventService) {
        this.scheduleEventService = scheduleEventService;
    }

    @PostMapping()
    public ResponseEntity<Object> registerEvent(@RequestBody @Valid ScheduleEventRequestDto scheduleEventRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleEventService.registerEvent(scheduleEventRequestDto));
    }
}
