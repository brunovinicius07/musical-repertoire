package com.music.services;

import com.music.model.dto.request.MusicRequestDto;
import com.music.model.dto.response.MusicResponseDto;
import com.music.model.entity.Music;

import java.util.List;

public interface MusicService {

    MusicResponseDto registerMusic(MusicRequestDto musicRequestDto);

    MusicResponseDto getMusicById(Long idMusic);

    List<MusicResponseDto> getAllMusicByIdUser(Long idUser);

    List<MusicResponseDto> getAllMusicByIdBlockMusic(Long idBlockMusic);

    MusicResponseDto updateMusic(Long idMusic, MusicRequestDto musicRequestDto);

    String deleteMusic(Long idMusic);

    void existingMusic(String nameMusic, String singer, Long idUser);

    Music validateMusic(Long idMusic);

}
