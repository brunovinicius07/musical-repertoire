package com.music.controllers;

import com.music.model.dto.request.BlockMusicRequestDto;
import com.music.model.dto.request.MusicToBlockRequest;
import com.music.model.dto.response.BlockMusicResponseDto;
import com.music.model.dto.response.MusicResponseDto;
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

    @PostMapping("/post")
    public ResponseEntity<BlockMusicResponseDto> registerBlockMusic(@RequestBody @Valid BlockMusicRequestDto blockMusicRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(blockMusicService.registerBlockMusic(blockMusicRequestDto));
    }

    @PostMapping("/{cdUser}")
    public ResponseEntity<List<BlockMusicResponseDto>> getAllBlockMusic(@PathVariable Long cdUser) {
        return ResponseEntity.ok(blockMusicService.getAllBlockMusic(cdUser));
    }


    @GetMapping("/{cdBlockMusic}")
    public ResponseEntity<BlockMusicResponseDto> getBlockMusicByCdBlockMusic(@PathVariable Long cdBlockMusic) {
        return ResponseEntity.ok(blockMusicService.getBlockMusicByCdBlockMusic(cdBlockMusic));
    }

    @PutMapping("/put/{cdBlockMusic}")
    public ResponseEntity<BlockMusicResponseDto> updateGender(@PathVariable Long cdBlockMusic, @RequestBody @Valid BlockMusicRequestDto blockMusicRequestDto) {
        return ResponseEntity.ok(blockMusicService.updateBlockMusic(cdBlockMusic, blockMusicRequestDto));
    }

    @PutMapping("/link-music-to-block/{cdBlockMusic}")
    public ResponseEntity<MusicResponseDto> linkMusicToBLock(@PathVariable Long cdBlockMusic, @RequestBody MusicToBlockRequest musicToBlockRequest){
        return ResponseEntity.ok(blockMusicService.linkMusicToBLock(cdBlockMusic, musicToBlockRequest));
    }

    @DeleteMapping("/delete/{cdBlockMusic}")
    public ResponseEntity<Object> deleteGender(@PathVariable Long cdBlockMusic) {
        return ResponseEntity.ok(blockMusicService.deleteBlockMusic(cdBlockMusic));
    }
}
