package com.music.services;

import com.music.model.dto.request.MusicRequestDto;
import com.music.model.dto.response.MusicResponseDto;

public interface MusicService {
    MusicResponseDto registerMusic(MusicRequestDto musicRequestDto);
}
