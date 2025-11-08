package com.music.services.impl;

import com.music.model.dto.request.MusicRequestDto;
import com.music.model.dto.response.MusicResponseDto;
import com.music.model.entity.Music;
import com.music.model.exceptions.music.MusicIsPresentException;
import com.music.model.exceptions.music.MusicNotFoundException;
import com.music.model.mapper.MusicMapper;
import com.music.repositories.BlockMusicRepository;
import com.music.repositories.MusicRepository;
import com.music.services.BlockMusicService;
import com.music.services.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MusicServiceImpl implements MusicService {

    private final MusicMapper musicMapper;

    private final MusicRepository musicRepository;

    private final BlockMusicService musicService;

    private final BlockMusicRepository blockMusicRepository;

    @Override
    @Transactional(readOnly = false)
    public MusicResponseDto registerMusic(MusicRequestDto musicRequestDto) {

        existingMusic(musicRequestDto.getNameMusic(), musicRequestDto.getSinger(), musicRequestDto.getIdUser());

        Music music = musicMapper.toMusic(musicRequestDto);

        if (musicRequestDto.getIdBlockMusics() != null && !musicRequestDto.getIdBlockMusics().isEmpty()) {
            var blockMusics = musicService.getBlockMusicsByIdBlockMusics(musicRequestDto.getIdBlockMusics());
            music.setBlockMusics(blockMusics);
            blockMusics.forEach(blockMusic -> blockMusic.getMusics().add(music));
        }

        return musicMapper.toMusicResponseDto(musicRepository.save(music));
    }

    @Override
    @Transactional(readOnly = true)
    public MusicResponseDto getMusicById(Long idMusic) {
        Music music = validateMusic(idMusic);

        return musicMapper.toMusicResponseDto(music);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MusicResponseDto> getAllMusicByIdUser(Long idUser) {
        List<Music> musicList = musicRepository.findAllMusicByUserIdUser(idUser);
        if (musicList.isEmpty()) throw new MusicNotFoundException();

        return musicMapper.toListMusicResponseDto(musicList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MusicResponseDto> getAllMusicByIdBlockMusic(Long idBlockMusic) {
        List<Music> musicList = musicRepository.findAllMusicByBlockMusicsIdBlockMusic(idBlockMusic);
        if (musicList.isEmpty()) throw new MusicNotFoundException();

        return musicMapper.toListMusicResponseDto(musicList);
    }

    @Override
    @Transactional(readOnly = false)
    public MusicResponseDto updateMusic(Long idMusic, MusicRequestDto musicRequestDto) {
        Music music = validateMusic(idMusic);
        music.setNameMusic(musicRequestDto.getNameMusic() != null
                ? musicRequestDto.getNameMusic() : music.getNameMusic());
        music.setSinger(musicRequestDto.getSinger() != null
                ? musicRequestDto.getSinger() : music.getSinger());
        music.setLetterMusic(musicRequestDto.getLetterMusic() != null
                ? musicRequestDto.getLetterMusic() : music.getLetterMusic());

        return musicMapper.toMusicResponseDto(musicRepository.save(music));
    }

    @Override
    @Transactional(readOnly = false)
    public String deleteMusic(Long idMusic) {
        Music music = validateMusic(idMusic);


        if (music.getBlockMusics() != null && !music.getBlockMusics().isEmpty()) {

            music.getBlockMusics().forEach(blockMusic -> {
                blockMusic.getMusics().remove(music);
                blockMusicRepository.save(blockMusic);
            });
        }

        music.setBlockMusics(null);
        musicRepository.delete(music);

        return "Música com ID " + idMusic + " excluída com sucesso!";
    }

    @Override
    @Transactional(readOnly = true)
    public void existingMusic(String nameMusic, String singer, Long idUser) {
        musicRepository.findByNameMusicAndSingerAndUserIdUser(nameMusic, singer, idUser)
                .ifPresent(music -> {
                    throw new MusicIsPresentException();
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Music validateMusic(Long idMusic) {
        return musicRepository.findById(idMusic).orElseThrow(MusicNotFoundException::new);
    }
}
