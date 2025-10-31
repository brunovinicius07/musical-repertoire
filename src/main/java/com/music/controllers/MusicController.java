package com.music.controllers;

import com.music.model.dto.request.MusicRequestDto;
import com.music.model.dto.response.MusicResponseDto;
import com.music.services.MusicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController()
@RequestMapping(value = "v1/music/musics")
public class MusicController {

    private final MusicService musicService;

    @PostMapping("/post")
    public ResponseEntity<MusicResponseDto> registerMusic(@RequestBody @Valid MusicRequestDto musicRequestDto) {
        var musicResponse = musicService.registerMusic(musicRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(musicResponse);
    }

    @PostMapping("/{cdUser}")
    public ResponseEntity<List<MusicResponseDto>> getAllMusicByCdUser(@PathVariable Long cdUser) {
        var musicResponse = musicService.getAllMusicByCdUser(cdUser);
        return ResponseEntity.ok(musicResponse);
    }

    @GetMapping("/list-music/{cdBlockMusic}")
    public ResponseEntity<List<MusicResponseDto>> getAllMusicByCdBlockMusic(@PathVariable Long cdBlockMusic){
        var musicResponse = musicService.getAllMusicByCdBlockMusic(cdBlockMusic);
        return ResponseEntity.ok(musicResponse);
    }

    @GetMapping("/{cdMusic}")
    public ResponseEntity<MusicResponseDto> getMusicById(@PathVariable Long cdMusic) {
        var musicResponse = musicService.getMusicById(cdMusic);
        return ResponseEntity.ok(musicResponse);
    }

    @PutMapping("/put/{cdMusic}")
    public ResponseEntity<MusicResponseDto> updateMusic(@PathVariable Long cdMusic,
                                                        @RequestBody @Valid MusicRequestDto musicRequestDto) {
        var musicResponse = musicService.updateMusic(cdMusic,musicRequestDto);
        return ResponseEntity.ok(musicResponse);
    }

    @DeleteMapping("/delete/{cdMusic}")
    public ResponseEntity<String> deleteMusic(@PathVariable Long cdMusic) {
        String message = musicService.deleteMusic(cdMusic);
        return ResponseEntity.ok(message);
    }
}
