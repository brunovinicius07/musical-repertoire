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

    @PostMapping
    public ResponseEntity<Object> registerMusic(@RequestBody @Valid MusicRequestDto musicRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(musicService.registerMusic(musicRequestDto));
    }

    @GetMapping
    public ResponseEntity<Object> getAllMusic(){
        return ResponseEntity.ok(musicService.getAllMusic());
    }

//    @GetMapping("/{cdMusic}")
//    public ResponseEntity<Object> getMusicById(@PathVariable Long cdMusic){
//        return ResponseEntity.ok(musicService.getMusicById(cdMusic));
//    }



}
