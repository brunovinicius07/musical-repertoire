package com.music.controllers;

import com.music.model.dto.request.ScheduleEventRequestDto;
import com.music.model.dto.response.ScheduleEventResponseDto;
import com.music.services.ScheduleEventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(value = "v1/music/event")
public class ScheduleEventController {

    private final ScheduleEventService scheduleEventService;

    public ScheduleEventController(ScheduleEventService scheduleEventService) {
        this.scheduleEventService = scheduleEventService;
    }

    @PostMapping()
    public ResponseEntity<ScheduleEventResponseDto> registerEvent(
            @RequestBody @Valid ScheduleEventRequestDto scheduleEventRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleEventService
                .registerEvent(scheduleEventRequestDto));
    }

    @PutMapping("/{cdScheduleEvent}")
    public ResponseEntity<ScheduleEventResponseDto> updateScheduleEvent(
            @PathVariable Long cdScheduleEvent,
            @RequestBody @Valid ScheduleEventRequestDto scheduleEventRequestDto){
        return ResponseEntity.ok(scheduleEventService.updateSheduleEvent(cdScheduleEvent, scheduleEventRequestDto));
    }

    @DeleteMapping("/{cdScheduleEvent}")
    public ResponseEntity<String> deleteScheduleEvent(@PathVariable Long cdScheduleEvent){
        return ResponseEntity.ok(scheduleEventService.deleteScheduleEvent(cdScheduleEvent));
    }
}
