package com.music.services;

import com.music.model.dto.request.MusicRequestDto;
import com.music.model.dto.response.MusicResponseDto;
import com.music.model.entity.Music;

import java.util.List;

public interface MusicService {

    MusicResponseDto registerMusic(MusicRequestDto musicRequestDto);

    void existingMusic(String nmMusic, String singer, Long cdUser);

    MusicResponseDto getMusicById(Long cdMusic);

    Music validateMusic(Long cdMusic);

    List<MusicResponseDto> getAllMusicByCdUser(Long cdUser);

    List<MusicResponseDto> getAllMusicByCdBlockMusic(Long cdBlockMusic);

    MusicResponseDto updateMusic(Long cdMusic, MusicRequestDto musicRequestDto);

    Object deleteMusic(Long cdMusic);

}
