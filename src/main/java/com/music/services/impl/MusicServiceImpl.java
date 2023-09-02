package com.music.services.impl;

import com.music.exception.AlertException;
import com.music.model.dto.request.MusicRequestDto;
import com.music.model.dto.response.MusicResponseDto;
import com.music.model.entity.Gender;
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

    private MusicMapper musicMapper;

    private MusicRepository musicRepository;


    private GenderServiceImpl genderServiceImp;

    public MusicServiceImpl(MusicMapper musicMapper, MusicRepository musicRepository, GenderServiceImpl genderServiceImp) {
        this.musicMapper = musicMapper;
        this.musicRepository = musicRepository;
        this.genderServiceImp = genderServiceImp;
    }

    @Override
    public MusicResponseDto registerMusic(MusicRequestDto musicRequestDto){
        existingMusic(musicRequestDto.getNmMusic(), musicRequestDto.getSinger());
        Gender gender = genderServiceImp.validateGender(musicRequestDto.getCdGender());

        Music music = musicMapper.toMusic(musicRequestDto);
        music.setGender(gender);

        return musicMapper.toMusicResponseDtoRegister(musicRepository.save(music));
    }

    @Override
    public List<MusicResponseDto> getAllMusic(){
        List<Music> musicList = validateListMusic();

        return musicList.stream().map((x) -> musicMapper.toMusicResponseDto(x)).collect(Collectors.toList());
    }

    @Override
    public MusicResponseDto getMusicById(Long cdMusic){
        var music = validateMusic(cdMusic);

        return musicMapper.toMusicResponseDto(music);
    }

    @Override
    public  MusicResponseDto updateMusic(Long cdMusic, MusicRequestDto musicRequestDto){
        Music music = validateMusic(cdMusic);
        music.setNmMusic(musicRequestDto.getNmMusic() != null ? musicRequestDto.getNmMusic() : music.getNmMusic());
        music.setSinger(musicRequestDto.getSinger() != null ? musicRequestDto.getSinger() : music.getSinger());

        return musicMapper.toMusicResponseDto(musicRepository.save(music));
    }

    public String deleteMusic(Long cdMusic){
        Music music = validateMusic(cdMusic);
        musicRepository.delete(music);

        return "Musica com ID " + cdMusic + " excluído com sucesso!";
    }

    public void existingMusic(String nmMusic, String singer){
        Optional<Music> optionalMusic = musicRepository.findByNmMusicAndSinger(nmMusic, singer);

        if (optionalMusic.isPresent()){
            throw new AlertException(
                    "warn",
                    String.format("Música %S já está cadastrada!", nmMusic,singer ),
                    HttpStatus.CONFLICT
            );
        }
    }

    public Music validateMusic(Long cdMusic){
        Optional<Music> optionalMusic = musicRepository.findById(cdMusic);

        if(optionalMusic.isEmpty()){
            throw new AlertException(
                    "warn",
                    String.format("Música com id %S não cadastrado!" , cdMusic),
                    HttpStatus.NOT_FOUND
            );
        }
        return optionalMusic.get();
    }

    public List<Music> validateListMusic(){
        List<Music>musicList = musicRepository.findAll();

        if (musicList.isEmpty()){
            throw new AlertException(
                    "warn",
                    String.format("Nenhuma música encontrada!"),
                    HttpStatus.NOT_FOUND
            );
        }
        return musicList;
    }
}
