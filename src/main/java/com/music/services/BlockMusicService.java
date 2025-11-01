package com.music.services;

import com.music.model.dto.request.BlockMusicRequestDto;
import com.music.model.dto.request.MusicToBlockRequest;
import com.music.model.dto.response.BlockMusicResponseDto;
import com.music.model.dto.response.MusicResponseDto;
import com.music.model.entity.BlockMusic;

import java.util.List;

public interface BlockMusicService {
    BlockMusicResponseDto registerBlockMusic(BlockMusicRequestDto blockMusicRequestDto);

    void existingBlockMusic(String nameBlockMusic, Long idUser);

    List<BlockMusicResponseDto> getAllBlockMusic(Long idUser);

    BlockMusicResponseDto getBlockMusicByIdBlockMusic(Long idBlockMusic);

    BlockMusic validateBlockMusic(Long idBlockMusic);

    BlockMusicResponseDto updateBlockMusic(Long idBlockMusic, BlockMusicRequestDto blockMusicRequestDto);

    String deleteBlockMusic(Long idBlockMusic);

    List<BlockMusic> getBlockMusicByIdBlockMusics(List<Long> idBlockMusics);

    MusicResponseDto linkMusicToBLock(Long idMusic, MusicToBlockRequest musicToBlockRequest);
}
