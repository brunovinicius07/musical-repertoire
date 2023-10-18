package com.music.services.impl;

import com.music.exception.AlertException;
import com.music.model.dto.request.MusicRequestDto;
import com.music.model.dto.response.MusicResponseDto;
import com.music.model.entity.Gender;
import com.music.model.entity.Music;
import com.music.model.mapper.MusicMapper;
import com.music.repositories.GenderRepository;
import com.music.repositories.MusicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class MusicServiceImplTest {

    public static final long CD_GENDER = 1L;
    public static final String NM_GENDER = "MPB";
    public static final List<Music> MUSICS = new ArrayList<>();
    public static final long CD_MUSIC = 1L;
    public static final String NM_MUSIC = "Oceano";
    public static final String SINGER = "Djavan";

    @Autowired
    private MusicServiceImpl musicService;

    @Autowired
    private GenderServiceImpl genderService;

    @MockBean
    private MusicRepository musicRepository;

    @MockBean
    private MusicMapper musicMapper;

    @MockBean
    private GenderRepository genderRepository;

    private Music music;

    private MusicResponseDto musicResponseDto;

    private Optional<Music> optionalMusic;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startMusic();
    }

    @Test
    void registerMusic() {
    }

    @Test
    void existingMusic() {
    }

    @Test
    void getAllMusic() {
    }

    @Test
    void validateListMusic() {
    }

    @Test
    void getMusicById_success() {
        when(musicRepository.findById(CD_MUSIC)).thenReturn(optionalMusic);
        when(musicMapper.toMusicResponseDto(any())).thenReturn(musicResponseDto);


        MusicResponseDto response = musicService.getMusicById(CD_MUSIC);

        verify(musicRepository, times(1)).findById(CD_MUSIC);
        verify(musicMapper, times(1)).toMusicResponseDto(music);
        verifyNoMoreInteractions(musicRepository, musicMapper);
        verify(musicRepository).findById(CD_MUSIC);
        verify(musicMapper).toMusicResponseDto(music);

        assertNotNull(response);
        assertEquals(musicResponseDto, response);
        assertEquals(MusicResponseDto.class, response.getClass());
        assertEquals(CD_MUSIC, response.getCdMusic());
        assertEquals(NM_MUSIC, response.getNmMusic());
        assertEquals(SINGER, response.getSinger());
        assertDoesNotThrow(() -> musicService.getMusicById(CD_MUSIC));
    }

    @Test
    void getMusicById_failure() {
        when(musicRepository.findById(anyLong())).thenReturn(Optional.empty());

        AlertException exception = assertThrows(AlertException.class, () -> {
            musicService.getMusicById(CD_MUSIC);
        });

        verify(musicRepository, times(1)).findById(CD_MUSIC);
        verify(musicMapper, never()).toMusicResponseDto(any());
        verify(musicRepository).findById(CD_MUSIC);
        verify(musicMapper, never()).toMusicResponseDto(any());

        assertNotNull(exception.getMessage());
        assertEquals("Música com id 1 não cadastrada!", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("warn", exception.getErrorCode());
        assertThrows(AlertException.class, () -> musicService.getMusicById(CD_MUSIC));
    }

    @Test
    void validateMusic_success() {
        when(musicRepository.findById(anyLong())).thenReturn(optionalMusic);
        when(musicMapper.toMusicResponseDto(any())).thenReturn(musicResponseDto);
        when(musicMapper.toMusic(any())).thenReturn(music);

        Music result = musicService.validateMusic(CD_MUSIC);

        verify(musicRepository, times(1)).findById(CD_MUSIC);
        verifyNoMoreInteractions(musicRepository, musicMapper);
        verify(musicMapper, never()).toMusicResponseDto(any());
        verify(musicRepository).findById(CD_MUSIC);

        assertNotNull(result);
        assertEquals(music, result);
        assertEquals(CD_MUSIC, result.getCdMusic());
        assertEquals(NM_MUSIC, result.getNmMusic());
        assertEquals(SINGER, result.getSinger());
        assertDoesNotThrow(() -> musicService.getMusicById(CD_MUSIC));
        assertDoesNotThrow(() -> musicService.validateMusic(CD_MUSIC));
    }

    @Test
    void validateMusic_failure() {
        when(musicRepository.findById(anyLong())).thenReturn(Optional.empty());

        AlertException exception = assertThrows(AlertException.class, () -> {
            musicService.validateMusic(CD_MUSIC);
        });

        verify(musicRepository, times(1)).findById(CD_GENDER);
        verify(musicMapper, never()).toMusicResponseDto(any());
        verify(musicRepository).findById(CD_MUSIC);
        verify(musicMapper, never()).toMusicResponseDto(any());

        assertNotNull(exception.getMessage());
        assertEquals("Música com id 1 não cadastrada!", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("warn", exception.getErrorCode());
        assertThrows(AlertException.class, () -> musicService.validateMusic(CD_MUSIC));
    }

    @Test
    void validateMusic() {
    }

    @Test
    void updateMusic() {
    }

    @Test
    void deleteMusic() {
    }

    private void startMusic() {
        Gender gender = new Gender(CD_GENDER, NM_GENDER, MUSICS);
        music = new Music(CD_MUSIC, NM_MUSIC, SINGER, gender);
        musicResponseDto = new MusicResponseDto(CD_MUSIC, NM_MUSIC, SINGER, gender.getNmGender());
        MusicRequestDto musicRequestDto = new MusicRequestDto(NM_MUSIC, SINGER, gender.getCdGender());
        optionalMusic = Optional.of(new Music(CD_MUSIC, NM_MUSIC, SINGER, gender));
        Optional<Gender> optionalGender = Optional.of(gender);
    }


}