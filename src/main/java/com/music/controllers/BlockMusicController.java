package com.music.controllers;

import com.music.model.dto.request.BlockMusicRequestDto;
import com.music.model.dto.request.MusicToBlockRequest;
import com.music.model.dto.response.BlockMusicResponseDto;
import com.music.model.dto.response.MusicResponseDto;
import com.music.services.BlockMusicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController()
@RequestMapping(value = "v1/music/block_music")
public class BlockMusicController {

    private final BlockMusicService blockMusicService;

    @PostMapping("/post")
    public ResponseEntity<BlockMusicResponseDto> registerBlockMusic(
            @RequestBody @Valid BlockMusicRequestDto blockMusicRequestDto) {
        var blockMusicResponse = blockMusicService.registerBlockMusic(blockMusicRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(blockMusicResponse);
    }

    @PostMapping("/{cdUser}")
    public ResponseEntity<List<BlockMusicResponseDto>> getAllBlockMusic(@PathVariable Long cdUser) {
        var blockMusicResponse = blockMusicService.getAllBlockMusic(cdUser);
        return ResponseEntity.ok(blockMusicResponse);
    }

    @GetMapping("/{cdBlockMusic}")
    public ResponseEntity<BlockMusicResponseDto> getBlockMusicByCdBlockMusic(@PathVariable Long cdBlockMusic) {
        var blockMusicResponse = blockMusicService.getBlockMusicByCdBlockMusic(cdBlockMusic);
        return ResponseEntity.ok(blockMusicResponse);
    }

    @PutMapping("/put/{cdBlockMusic}")
    public ResponseEntity<BlockMusicResponseDto> updateBlockMusic(@PathVariable Long cdBlockMusic,
                                                                  @RequestBody @Valid
                                                              BlockMusicRequestDto blockMusicRequestDto) {
        var blockMusicResponse = blockMusicService.updateBlockMusic(cdBlockMusic,blockMusicRequestDto);
        return ResponseEntity.ok(blockMusicResponse);
    }

    @PutMapping("/link-music-to-block/{cdBlockMusic}")
    public ResponseEntity<MusicResponseDto> linkMusicToBLock(@PathVariable Long cdBlockMusic,
                                                             @RequestBody
                                                             MusicToBlockRequest musicToBlockRequest){

        var blockMusicResponse = blockMusicService.linkMusicToBLock(cdBlockMusic,musicToBlockRequest);
        return ResponseEntity.ok(blockMusicResponse);
    }

    @DeleteMapping("/delete/{cdBlockMusic}")
    public ResponseEntity<String> deleteBlockMusic(@PathVariable Long cdBlockMusic) {
        String message = blockMusicService.deleteBlockMusic(cdBlockMusic);
        return ResponseEntity.ok(message);
    }
}
