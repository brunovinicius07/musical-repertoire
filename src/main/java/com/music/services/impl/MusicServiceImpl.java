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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<MusicResponseDto> getAllMusic(){

        List<Music>musicList = musicRepository.findAll();

        if (musicList.isEmpty()){
            throw new AlertException(
                    "warn",
                    String.format("Nenhuma música encontrada!"),
                    HttpStatus.NOT_FOUND
            );
        }
        return musicList.stream().map((x) -> musicMapper.toMusicResponseDto(x)).collect(Collectors.toList());
    }

    @Override
    public MusicResponseDto getMusicById(Long cdMusic){
        Optional<Music>musicOptional = musicRepository.findById(cdMusic);

        if(musicOptional.isEmpty()){
            throw new AlertException(
                    "warn",
                    String.format("Música do id %S não existe", cdMusic),
                    HttpStatus.NOT_FOUND
            );
        }
        return musicMapper.toMusicResponseDto(musicOptional.get());
    }


}
