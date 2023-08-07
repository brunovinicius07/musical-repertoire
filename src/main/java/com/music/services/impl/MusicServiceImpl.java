package com.music.services.impl;

import com.music.exception.AlertException;
import com.music.model.dto.request.MusicRequestDto;
import com.music.model.dto.response.MusicResponseDto;
import com.music.model.entity.Music;
import com.music.model.mapper.MusicMapper;
import com.music.repositories.MusicRepository;
import com.music.services.MusicService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MusicServiceImpl implements MusicService {

    private final MusicMapper musicMapper;

    private final MusicRepository musicRepository;


    public MusicServiceImpl(MusicMapper musicMapper, MusicRepository musicRepository) {
        this.musicMapper = musicMapper;
        this.musicRepository = musicRepository;
    }

    @Override
    public MusicResponseDto registerMusic(MusicRequestDto musicRequestDto){

        Optional<Music> musicOptional = musicRepository.findByNmMusicAndSinger(musicRequestDto.getNmMusic(), musicRequestDto.getSinger());

        if (musicOptional.isPresent()){
            throw new AlertException(
                    "warn",
                    String.format("Musica com o nome %S já está cadastrada!", musicRequestDto.getNmMusic()),
                    HttpStatus.CONFLICT
            );
        }
        return musicMapper.toMusicResponseDto(musicRepository.save(musicMapper.toMusic(musicRequestDto)));
    }


}
