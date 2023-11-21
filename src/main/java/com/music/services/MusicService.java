package com.music.services;

import com.music.model.dto.request.MusicRequestDto;
import com.music.model.dto.response.MusicResponseDto;

import java.util.List;

public interface MusicService {

    MusicResponseDto registerMusic(MusicRequestDto musicRequestDto);

    List<MusicResponseDto> getAllMusic(Long cdGender);

    MusicResponseDto getMusicById(Long cdMusic);

    MusicResponseDto updateMusic(Long cdMusic, MusicRequestDto musicRequestDto);

    Object deleteMusic(Long cdMusic);
}
