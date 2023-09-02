package com.music.services;

import com.music.model.dto.request.MusicPutRequestDto;
import com.music.model.dto.request.MusicRequestDto;
import com.music.model.dto.response.MusicResponseDto;

import java.util.List;

public interface MusicService {

    MusicResponseDto registerMusic(MusicRequestDto musicRequestDto);

    List<MusicResponseDto> getAllMusic();

    MusicResponseDto getMusicById(Long cdMusic);

    MusicResponseDto updateMusic(Long cdMusic, MusicPutRequestDto musicPutRequestDto);

    Object deleteMusic(Long cdMusic);
}
