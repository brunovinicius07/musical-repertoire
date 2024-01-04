package com.music.services;

import com.music.model.dto.request.BlockMusicRequestDto;
import com.music.model.dto.request.MusicToBlockRequest;
import com.music.model.dto.response.BlockMusicResponseDto;
import com.music.model.dto.response.MusicResponseDto;
import com.music.model.entity.BlockMusic;

import java.util.List;

public interface BlockMusicService {
    BlockMusicResponseDto registerBlockMusic(BlockMusicRequestDto blockMusicRequestDto);

    void existingBlockMusic(String nmGender, Long cdUser);

    List<BlockMusicResponseDto> getAllBlockMusic(Long cdUser);

    BlockMusicResponseDto getBlockMusicByCdBlockMusic(Long cdBlockMusic);

    BlockMusic validateBlockMusic(Long cdBlockMusic);

    BlockMusicResponseDto updateBlockMusic(Long cdBlockMusic, BlockMusicRequestDto blockMusicRequestDto);

    Object deleteBlockMusic(Long cdBlockMusic);

    List<BlockMusic> getBlockMusicByCdBlockMusics(List<Long> cdBlockMusics);

    MusicResponseDto linkMusicToBLock(Long cdMusic, MusicToBlockRequest musicToBlockRequest);
}
