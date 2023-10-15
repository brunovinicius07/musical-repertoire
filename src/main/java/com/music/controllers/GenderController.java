package com.music.controllers;

import com.music.model.dto.request.GenderRequestDto;
import com.music.services.GenderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(value = "v1/music/genders")
public class GenderController {

    private final GenderService genderService;

    public GenderController(GenderService genderService) {
        this.genderService = genderService;
    }

    @PostMapping("/post")
    public ResponseEntity<Object> registerGender(@RequestBody @Valid GenderRequestDto genderRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(genderService.registerGender(genderRequestDto));
    }

    @GetMapping("/get")
    public ResponseEntity<Object> getAllGender() {
        return ResponseEntity.ok(genderService.getAllGender());
    }

    @GetMapping("/{cdGender}")
    public ResponseEntity<Object> getGenderById(@PathVariable Long cdGender) {
        return ResponseEntity.ok(genderService.getGenderById(cdGender));
    }

    @PutMapping("/put/{cdGender}")
    public ResponseEntity<Object> updateGender(@PathVariable Long cdGender, @RequestBody @Valid GenderRequestDto genderRequestDto) {
        return ResponseEntity.ok(genderService.updateGender(cdGender, genderRequestDto));
    }

    @DeleteMapping("/delete/{cdGender}")
    public ResponseEntity<Object> deleteGender(@PathVariable Long cdGender) {
        return ResponseEntity.ok(genderService.deleteGender(cdGender));
    }
}
