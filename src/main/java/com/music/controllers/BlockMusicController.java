package com.music.controllers;

import com.music.model.dto.request.BlockMusicRequestDto;
import com.music.model.dto.response.BlockMusicResponseDto;
import com.music.services.BlockMusicService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(value = "v1/music/block_music")
public class BlockMusicController {

    private final BlockMusicService blockMusicService;

    public BlockMusicController(BlockMusicService blockMusicService) {
        this.blockMusicService = blockMusicService;
    }

    @PostMapping()
    public ResponseEntity<BlockMusicResponseDto> registerBlockMusic(@RequestBody @Valid BlockMusicRequestDto blockMusicRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(blockMusicService.registerBlockMusic(blockMusicRequestDto));
    }

    @PostMapping("/{cdUser}")
    public ResponseEntity<List<BlockMusicResponseDto>> getAllBlockMusic(@PathVariable Long cdUser) {
        return ResponseEntity.ok(blockMusicService.getAllBlockMusic(cdUser));
    }

    @GetMapping("/{blockMusic}")
    public ResponseEntity<BlockMusicResponseDto> getBlockMusicByCdBlockMusic(@PathVariable Long blockMusic) {
        return ResponseEntity.ok(blockMusicService.getBlockMusicByCdBlockMusic(blockMusic));
    }

    @PutMapping("{blockMusic}")
    public ResponseEntity<BlockMusicResponseDto> updateGender(@PathVariable Long blockMusic, @RequestBody @Valid BlockMusicRequestDto blockMusicRequestDto) {
        return ResponseEntity.ok(blockMusicService.updateBlockMusic(blockMusic, blockMusicRequestDto));
    }

    @DeleteMapping("{blockMusic}")
    public ResponseEntity<Object> deleteGender(@PathVariable Long blockMusic) {
        return ResponseEntity.ok(blockMusicService.deleteBlockMusic(blockMusic));
    }
}
