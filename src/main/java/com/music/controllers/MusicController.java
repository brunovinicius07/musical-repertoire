package com.music.controllers;

import com.music.model.dto.request.MusicRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "v1/music/musics")
public class MusicController {
//
//    @PostMapping
//    public ResponseEntity<Object> registerMusic(@RequestBody @Valid MusicRequestDto musicRequestDto){
//        return ResponseEntity.status(HttpStatus.CREATED).body()
//    }


}
