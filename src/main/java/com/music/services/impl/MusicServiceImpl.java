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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MusicServiceImpl implements MusicService {

    private final MusicMapper musicMapper;

    private final MusicRepository musicRepository;

    private final GenderServiceImpl genderServiceImp;

    public MusicServiceImpl(MusicMapper musicMapper, MusicRepository musicRepository, GenderServiceImpl genderServiceImp) {
        this.musicMapper = musicMapper;
        this.musicRepository = musicRepository;
        this.genderServiceImp = genderServiceImp;
    }

    @Override
    @Transactional(readOnly = false)
    public MusicResponseDto registerMusic(MusicRequestDto musicRequestDto) {
        existingMusic(musicRequestDto.getNmMusic(), musicRequestDto.getSinger(), musicRequestDto.getCdUser());
        Gender gender = genderServiceImp.validateGender(musicRequestDto.getCdGender());

        Music music = musicMapper.toMusic(musicRequestDto);
        music.setGender(gender);

        return musicMapper.toMusicResponseDto(musicRepository.save(music));
    }

    @Transactional(readOnly = true)
    public void existingMusic(String nmMusic, String singer, Long cdUser) {
        Optional<Music> optionalMusic = musicRepository.findByNmMusicAndSingerAndGenderUserCdUser(nmMusic, singer, cdUser);

        if (optionalMusic.isPresent()) {
            throw new AlertException(
                    "warn",
                    String.format("Música %S já está cadastrada!", nmMusic, singer),
                    HttpStatus.CONFLICT
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<MusicResponseDto> getAllMusic() {
        List<Music> musicList = validateListMusic();

        return musicList.stream().map(musicMapper::toMusicResponseDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Music> validateListMusic() {
        List<Music> musicList = musicRepository.findAll();

        if (musicList.isEmpty()) {
            throw new AlertException(
                    "warn",
                    ("Nenhuma música encontrada!"),
                    HttpStatus.NOT_FOUND
            );
        }
        return musicList;
    }

    @Override
    @Transactional(readOnly = true)
    public MusicResponseDto getMusicById(Long cdMusic) {
        var music = validateMusic(cdMusic);

        return musicMapper.toMusicResponseDto(music);
    }

    @Transactional(readOnly = true)
    public Music validateMusic(Long cdMusic) {
        Optional<Music> optionalMusic = musicRepository.findById(cdMusic);

        if (optionalMusic.isEmpty()) {
            throw new AlertException(
                    "warn",
                    String.format("Música com id %S não cadastrada!", cdMusic),
                    HttpStatus.NOT_FOUND
            );
        }
        return optionalMusic.get();
    }

    @Override
    @Transactional(readOnly = false)
    public MusicResponseDto updateMusic(Long cdMusic, MusicRequestDto musicRequestDto) {
        Music music = validateMusic(cdMusic);
        music.setNmMusic(musicRequestDto.getNmMusic());
        music.setSinger(musicRequestDto.getSinger());
        music.getGender().setCdGender(musicRequestDto.getCdGender());

        return musicMapper.toMusicResponseDto(musicRepository.save(music));
    }

    @Override
    @Transactional(readOnly = false)
    public String deleteMusic(Long cdMusic) {
        Music music = validateMusic(cdMusic);
        musicRepository.delete(music);

        return "Musica com ID " + cdMusic + " excluída com sucesso!";
    }
}
