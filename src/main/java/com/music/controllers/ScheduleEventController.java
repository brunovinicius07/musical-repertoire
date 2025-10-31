package com.music.controllers;

import com.music.model.dto.request.ScheduleEventRequestDto;
import com.music.model.dto.response.ScheduleEventResponseDto;
import com.music.services.ScheduleEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController()
@RequestMapping(value = "v1/music/event")
public class ScheduleEventController {

    private final ScheduleEventService scheduleEventService;

    @PostMapping()
    public ResponseEntity<ScheduleEventResponseDto> registerEvent(
            @RequestBody @Valid ScheduleEventRequestDto scheduleEventRequestDto){
        var scheduleEventResponse = scheduleEventService.registerEvent(scheduleEventRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleEventResponse);
    }

    @PutMapping("/{cdScheduleEvent}")
    public ResponseEntity<ScheduleEventResponseDto> updateScheduleEvent(
            @PathVariable Long cdScheduleEvent,
            @RequestBody @Valid ScheduleEventRequestDto scheduleEventRequestDto){
        var scheduleEventResponse = scheduleEventService.updateScheduleEvent(cdScheduleEvent,scheduleEventRequestDto);
        return ResponseEntity.ok(scheduleEventResponse);
    }

    @DeleteMapping("/{cdScheduleEvent}")
    public ResponseEntity<String> deleteScheduleEvent(@PathVariable Long cdScheduleEvent){
        String message = scheduleEventService.deleteScheduleEvent(cdScheduleEvent);
        return ResponseEntity.ok(message);
    }
}
