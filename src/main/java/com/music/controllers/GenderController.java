package com.music.controllers;

import com.music.model.dto.request.GenderRequestDto;
import com.music.services.GenderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(value = "v1/music/genders")
public class GenderController {

    @Autowired
    private GenderService genderService;

    @PostMapping
    public ResponseEntity<Object> registerGender(@RequestBody @Valid GenderRequestDto genderRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(genderService.registerGender(genderRequestDto));
    }



}
