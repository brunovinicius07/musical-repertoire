package com.music.services.impl;

import com.music.model.dto.request.MusicRequestDto;
import com.music.model.dto.response.MusicResponseDto;
import com.music.model.entity.Music;
import com.music.model.exceptions.Music.MusicIsPresentException;
import com.music.model.exceptions.Music.MusicNotFoundException;
import com.music.model.mapper.MusicMapper;
import com.music.repositories.MusicRepository;
import com.music.services.BlockMusicService;
import com.music.services.MusicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MusicServiceImpl implements MusicService {

    private final MusicMapper musicMapper;

    private final MusicRepository musicRepository;

    private final BlockMusicService musicService;

    public MusicServiceImpl(MusicMapper musicMapper, MusicRepository musicRepository, BlockMusicService musicService) {
        this.musicMapper = musicMapper;
        this.musicRepository = musicRepository;
        this.musicService = musicService;
    }

    @Override
    @Transactional(readOnly = false)
    public MusicResponseDto registerMusic(MusicRequestDto musicRequestDto) {

        existingMusic(musicRequestDto.getNmMusic(), musicRequestDto.getSinger(), musicRequestDto.getCdUser());

        Music music = musicMapper.toMusic(musicRequestDto);

        if (musicRequestDto.getCdBlockMusics() != null && !musicRequestDto.getCdBlockMusics().isEmpty()) {
            var blockMusics = musicService.getBlockMusicByCdBlockMusics(musicRequestDto.getCdBlockMusics());
            music.setBlockMusics(blockMusics);
            blockMusics.forEach(blockMusic -> blockMusic.getMusics().add(music));
        }

        return musicMapper.toMusicResponseDto(musicRepository.save(music));
    }

    @Override
    @Transactional(readOnly = true)
    public void existingMusic(String nmMusic, String singer, Long cdUser) {
        musicRepository.findByNmMusicAndSingerAndUserCdUser(nmMusic, singer, cdUser)
                .ifPresent(music -> {
            throw new MusicIsPresentException();
        });
    }

    @Override
    @Transactional(readOnly = true)
    public MusicResponseDto getMusicById(Long cdMusic) {
        Music music = validateMusic(cdMusic);

        return musicMapper.toMusicResponseDto(music);
    }

    @Override
    @Transactional(readOnly = true)
    public Music validateMusic(Long cdMusic) {
        return musicRepository.findById(cdMusic).orElseThrow(MusicNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MusicResponseDto> getAllMusicByCdUser(Long cdUser) {
        List<Music> musicList = musicRepository.findAllMusicByUserCdUser(cdUser);
        if (musicList.isEmpty()) throw new MusicNotFoundException();

        return musicMapper.toListMusicResponseDto(musicList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MusicResponseDto> getAllMusicByCdBlockMusic(Long cdBlockMusic) {
        List<Music> musicList = musicRepository.findAllMusicByBlockMusicsCdBlockMusic(cdBlockMusic);
        if (musicList.isEmpty()) throw new MusicNotFoundException();

        return musicMapper.toListMusicResponseDto(musicList);
    }

    @Override
    @Transactional(readOnly = false)
    public MusicResponseDto updateMusic(Long cdMusic, MusicRequestDto musicRequestDto) {
        Music music = validateMusic(cdMusic);
        music.setNmMusic(musicRequestDto.getNmMusic());
        music.setSinger(musicRequestDto.getSinger());
        music.setLetterMusic(musicRequestDto.getLetterMusic());

        return musicMapper.toMusicResponseDto(musicRepository.save(music));
    }

    @Override
    @Transactional(readOnly = false)
    public String deleteMusic(Long cdMusic) {
        Music music = validateMusic(cdMusic);
        musicRepository.delete(music);

        return "Música com ID " + cdMusic + " excluída com sucesso!";
    }
}
