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

    @Override
    public  MusicResponseDto updateMusic(Long cdMusic, MusicRequestDto musicRequestDto){
        Optional<Music> music = musicRepository.findById(cdMusic);

        if(music.isEmpty()){
            throw new AlertException(
                    "warn",
                    String.format("Música com id %S não existe!", cdMusic),
                    HttpStatus.NOT_FOUND
            );
        }
        music.get().setNmMusic(musicRequestDto.getNmMusic() != null ? musicRequestDto.getNmMusic() : music.get().getNmMusic());
        music.get().setSinger(musicRequestDto.getSinger() != null ? musicRequestDto.getSinger() : music.get().getSinger());

        return musicMapper.toMusicResponseDto(musicRepository.save(music.get()));
    }

    public String deleteMusic(Long cdMusic){
        Music existingMusic = musicRepository.findById(cdMusic)
                .orElseThrow(()-> new AlertException(
                        "warn",
                        String.format("Music com id %S não encontrado!", cdMusic),
                        HttpStatus.NOT_FOUND
                ));
        musicRepository.delete(existingMusic);
        return "Musica com ID " + cdMusic + " excluído com sucesso!";
    }


}
