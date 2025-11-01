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

    @PostMapping("/{idUser}")
    public ResponseEntity<List<MusicResponseDto>> getAllMusicByIdUser(@PathVariable Long idUser) {
        var musicResponse = musicService.getAllMusicByIdUser(idUser);
        return ResponseEntity.ok(musicResponse);
    }

    @GetMapping("/list-music/{idBlockMusic}")
    public ResponseEntity<List<MusicResponseDto>> getAllMusicByIdBlockMusic(@PathVariable Long idBlockMusic){
        var musicResponse = musicService.getAllMusicByIdBlockMusic(idBlockMusic);
        return ResponseEntity.ok(musicResponse);
    }

    @GetMapping("/{idMusic}")
    public ResponseEntity<MusicResponseDto> getMusicById(@PathVariable Long idMusic) {
        var musicResponse = musicService.getMusicById(idMusic);
        return ResponseEntity.ok(musicResponse);
    }

    @PutMapping("/put/{idMusic}")
    public ResponseEntity<MusicResponseDto> updateMusic(@PathVariable Long idMusic,
                                                        @RequestBody @Valid MusicRequestDto musicRequestDto) {
        var musicResponse = musicService.updateMusic(idMusic,musicRequestDto);
        return ResponseEntity.ok(musicResponse);
    }

    @DeleteMapping("/delete/{idMusic}")
    public ResponseEntity<String> deleteMusic(@PathVariable Long idMusic) {
        String message = musicService.deleteMusic(idMusic);
        return ResponseEntity.ok(message);
    }
}
