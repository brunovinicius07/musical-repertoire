package com.music.controllers;

import com.music.model.dto.request.RepertoireRequestDto;
import com.music.model.dto.response.RepertoireResponseDto;
import com.music.services.RepertoireService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(value = "v1/music/repertoire")
public class RepertoireController {

    private final RepertoireService repertoireService;

    public RepertoireController(RepertoireService repertoireService) {
        this.repertoireService = repertoireService;
    }

    @PostMapping("/post")
    ResponseEntity<RepertoireResponseDto> registerRepertoire(@RequestBody @Valid RepertoireRequestDto repertoireRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(repertoireService.registerRepertoire(repertoireRequestDto));
    }

    @PostMapping("/{cdUser}")
    ResponseEntity<List<RepertoireResponseDto>> getAllRepertoireByCdUser(@PathVariable Long cdUser){
        return ResponseEntity.ok(repertoireService.getAllRepertoireByCdUser(cdUser));
    }

    @GetMapping("/{cdRepertoire}")
    ResponseEntity<RepertoireResponseDto> getRepertoireByCdRepertoire(@PathVariable Long cdRepertoire){
        return ResponseEntity.ok(repertoireService.getRepertoireByCdRepertoire(cdRepertoire));
    }

    @PutMapping("/put/{cdRepertoire}")
    ResponseEntity<RepertoireResponseDto> updadeRepertoire(@PathVariable Long cdRepertoire, @RequestBody
                                                           @Valid RepertoireRequestDto repertoireRequestDto){
        return ResponseEntity.ok(repertoireService.updateRepertoire(cdRepertoire, repertoireRequestDto));
    }

    @DeleteMapping("delete/{cdRepertoire}")
    ResponseEntity<Object> deleteRepertoire(@PathVariable Long cdRepertoire){
        return ResponseEntity.ok(repertoireService.deleteRepertoire(cdRepertoire));
    }
}
