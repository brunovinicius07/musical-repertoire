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
        var scheduleEventResponse = scheduleEventService.createEvent(scheduleEventRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleEventResponse);
    }

    @PutMapping("/{idScheduleEvent}")
    public ResponseEntity<ScheduleEventResponseDto> updateScheduleEvent(
            @PathVariable Long idScheduleEvent,
            @RequestBody @Valid ScheduleEventRequestDto scheduleEventRequestDto){
        var scheduleEventResponse = scheduleEventService.updateScheduleEvent(idScheduleEvent,scheduleEventRequestDto);
        return ResponseEntity.ok(scheduleEventResponse);
    }

    @DeleteMapping("/{idScheduleEvent}")
    public ResponseEntity<String> deleteScheduleEvent(@PathVariable Long idScheduleEvent){
        String message = scheduleEventService.deleteScheduleEvent(idScheduleEvent);
        return ResponseEntity.ok(message);
    }
}
