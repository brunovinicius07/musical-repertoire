package com.music.controllers;

import com.music.model.dto.request.RepertoireRequestDto;
import com.music.model.dto.response.RepertoireResponseDto;
import com.music.services.RepertoireService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController()
@RequestMapping(value = "v1/music/repertoire")
public class RepertoireController {

    private final RepertoireService repertoireService;

    @PostMapping("/post")
    ResponseEntity<RepertoireResponseDto> registerRepertoire(
            @RequestBody @Valid RepertoireRequestDto repertoireRequestDto){
        var repertoireResponse = repertoireService.registerRepertoire(repertoireRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(repertoireResponse);
    }

    @PostMapping("/{cdUser}")
    ResponseEntity<List<RepertoireResponseDto>> getAllRepertoireByCdUser(@PathVariable Long cdUser){
        var repertoireResponse = repertoireService.getAllRepertoireByCdUser(cdUser);
        return ResponseEntity.ok(repertoireResponse);
    }

    @GetMapping("/{cdRepertoire}")
    ResponseEntity<RepertoireResponseDto> getRepertoireByCdRepertoire(@PathVariable Long cdRepertoire){
        var repertoireResponse = repertoireService.getRepertoireByCdRepertoire(cdRepertoire);
        return ResponseEntity.ok(repertoireResponse);
    }

    @PutMapping("/put/{cdRepertoire}")
    ResponseEntity<RepertoireResponseDto> updateRepertoire(@PathVariable Long cdRepertoire, @RequestBody
                                                           @Valid RepertoireRequestDto repertoireRequestDto){
        var repertoireResponse = repertoireService.updateRepertoire(cdRepertoire,repertoireRequestDto);
        return ResponseEntity.ok(repertoireResponse);
    }

    @DeleteMapping("delete/{cdRepertoire}")
    ResponseEntity<String> deleteRepertoire(@PathVariable Long cdRepertoire){
        String message = repertoireService.deleteRepertoire(cdRepertoire);
        return ResponseEntity.ok(message);
    }
}
