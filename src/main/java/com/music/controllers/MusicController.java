package com.music.controllers;

import com.music.model.dto.request.MusicRequestDto;
import com.music.services.MusicService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(value = "v1/music/musics")
public class MusicController {

    private final MusicService musicService;

    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }


    @PostMapping()
    public ResponseEntity<Object> registerMusic(@RequestBody @Valid MusicRequestDto musicRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(musicService.registerMusic(musicRequestDto));
    }

    @PostMapping("/{cdUser}")
    public ResponseEntity<Object> getAllMusic(@PathVariable Long cdUser) {
        return ResponseEntity.ok(musicService.getAllMusic(cdUser));
    }

    @GetMapping("/{cdMusic}")
    public ResponseEntity<Object> getMusicById(@PathVariable Long cdMusic) {
        return ResponseEntity.ok(musicService.getMusicById(cdMusic));
    }

    @PutMapping("/put/{cdMusic}")
    public ResponseEntity<Object> updateMusic(@PathVariable Long cdMusic, @RequestBody @Valid MusicRequestDto musicRequestDto) {
        return ResponseEntity.ok(musicService.updateMusic(cdMusic, musicRequestDto));
    }

    @DeleteMapping("/delete/{cdMusic}")
    public ResponseEntity<Object> deleteMusic(@PathVariable Long cdMusic) {
        return ResponseEntity.ok(musicService.deleteMusic(cdMusic));
    }
}
