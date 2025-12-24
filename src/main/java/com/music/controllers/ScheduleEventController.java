package com.music.controllers;

import com.music.infra.feign.ScheduleEventClient;
import com.music.model.dto.request.ScheduleEventRequest;
import com.music.model.dto.response.ScheduleEventResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/music/event")
public class ScheduleEventController {

    private final ScheduleEventClient client;

    @PostMapping("/post")
    public ResponseEntity<ScheduleEventResponse> registerEvent(
            @RequestBody @Valid ScheduleEventRequest request) {

        ScheduleEventResponse response = client.createEvent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/day")
    public ResponseEntity<List<ScheduleEventResponse>> getEventsByUserAndDay(
            @RequestParam Long userId,
            @RequestParam String day) {

        return ResponseEntity.ok(
                client.getEventsByUserAndDay(userId, day)
        );
    }

    @GetMapping("/month")
    public ResponseEntity<List<Integer>> getDaysWithEventsInMonth(
            @RequestParam Long userId,
            @RequestParam int year,
            @RequestParam int month) {

        return ResponseEntity.ok(
                client.getDaysWithEventsInMonth(userId, year, month)
        );
    }

    @GetMapping("/range")
    public ResponseEntity<List<ScheduleEventResponse>> getEventsByRange(
            @RequestParam Long userId,
            @RequestParam String start,
            @RequestParam String end) {

        return ResponseEntity.ok(
                client.getEventsByRange(userId, start, end)
        );
    }
}

