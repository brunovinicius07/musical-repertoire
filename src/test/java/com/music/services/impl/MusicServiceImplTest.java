package com.music.services.impl;

import com.music.exception.AlertException;
import com.music.model.dto.request.MusicRequestDto;
import com.music.model.dto.response.MusicResponseDto;
import com.music.model.entity.Gender;
import com.music.model.entity.Music;
import com.music.model.entity.User;
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

    public static final long CD_USER = 1L;

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

    @MockBean
    private Optional<Gender> optionalGender;

    private Music music;

    private MusicResponseDto musicResponseDto;

    private MusicRequestDto musicRequestDto;

    private Optional<Music> optionalMusic;

    private Gender gender;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startMusic();
    }

    @Test
    void registerMusic_success() {
        when(genderRepository.findById(anyLong())).thenReturn(Optional.of(gender));
        when(musicMapper.toMusic(any())).thenReturn(music);
        when(musicRepository.save(any())).thenReturn(music);
        when(musicMapper.toMusicResponseDto(any())).thenReturn(musicResponseDto);

        MusicResponseDto response = musicService.registerMusic(musicRequestDto);

        verify(musicRepository, times(1)).save(any());
        verify(musicMapper, times(1)).toMusicResponseDto(any(Music.class));
        verify(musicMapper, times(1)).toMusic(any());
        verifyNoMoreInteractions(musicMapper);

        assertNotNull(response);
        assertEquals(musicResponseDto, response);
        assertEquals(CD_MUSIC, response.getCdMusic());
        assertEquals(NM_MUSIC, response.getNmMusic());
        assertEquals(SINGER, response.getSinger());
        assertEquals(NM_GENDER, response.getNmGender());
    }

    @Test
    void registerMusic_failure() {
        when(genderRepository.findById(anyLong())).thenReturn(Optional.of(gender));
        when(musicMapper.toMusic(any())).thenReturn(music);
        when(musicRepository.save(any())).thenReturn(music);
        when(musicRepository.findByNmMusicAndSingerAndGenderUserCdUser(NM_MUSIC, SINGER,CD_USER)).thenReturn(optionalMusic);
        when(musicMapper.toMusicResponseDto(any())).thenReturn(musicResponseDto);

        MusicRequestDto resquest = musicRequestDto;

        AlertException exception = assertThrows(AlertException.class, () -> {
            musicService.registerMusic(resquest);
        });

        verify(musicMapper, never()).toMusicResponseDto(any());

        assertNotNull(exception.getMessage());
        assertEquals("Música OCEANO já está cadastrada!", exception.getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getHttpStatus());
        assertEquals("warn", exception.getErrorCode());
    }

    @Test
    void existingMusic_success() {
        when(musicRepository.findByNmMusicAndSingerAndGenderUserCdUser(NM_MUSIC, SINGER, CD_USER)).thenReturn(Optional.empty());
        when(musicMapper.toMusicResponseDto(any())).thenReturn(musicResponseDto);

        musicService.existingMusic(NM_MUSIC, SINGER, CD_USER);

        verify(musicRepository, times(1)).findByNmMusicAndSingerAndGenderUserCdUser(NM_MUSIC, SINGER, CD_USER);
        verifyNoMoreInteractions(musicRepository, musicMapper);
        verify(musicMapper, never()).toMusicResponseDto(any());
        verifyNoMoreInteractions(musicRepository);
        verifyNoMoreInteractions(musicMapper);
    }

    @Test
    void existingGender_failure() {
        when(musicRepository.findByNmMusicAndSingerAndGenderUserCdUser(NM_MUSIC, SINGER, CD_USER)).thenReturn(optionalMusic);
        when(musicMapper.toMusicResponseDto(any())).thenReturn(musicResponseDto);

        AlertException exception = assertThrows(AlertException.class, () -> {
            musicService.existingMusic(NM_MUSIC, SINGER, CD_USER);
        });
        verify(musicRepository, times(1)).findByNmMusicAndSingerAndGenderUserCdUser(NM_MUSIC, SINGER,CD_USER);
        verifyNoMoreInteractions(musicRepository, musicMapper);
        verify(musicMapper, never()).toMusicResponseDto(any());

        assertNotNull(exception.getMessage());
        assertEquals("Música OCEANO já está cadastrada!", exception.getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getHttpStatus());
        assertEquals("warn", exception.getErrorCode());
        assertThrows(AlertException.class, () -> musicService.existingMusic(NM_MUSIC, SINGER,CD_USER));
    }

    @Test
    void getAllMusic_success() {
        when(musicRepository.findAll()).thenReturn(List.of(music));
        when(musicMapper.toMusicResponseDto(any())).thenReturn(musicResponseDto);

        List<MusicResponseDto> response = musicService.getAllMusic(CD_GENDER);

        verify(musicRepository, times(1)).findAll();
        verify(musicMapper, times(1)).toMusicResponseDto(any());
        verifyNoMoreInteractions(musicRepository, musicMapper);

        assertNotNull(response);
        assertEquals(CD_MUSIC, response.get(0).getCdMusic());
        assertEquals(NM_MUSIC, response.get(0).getNmMusic());
        assertEquals(SINGER, response.get(0).getSinger());
    }

    @Test
    void getAllMusic_failure() {
        when(musicRepository.findAll()).thenReturn(List.of());
        when(musicMapper.toMusicResponseDto(any())).thenReturn(musicResponseDto);

        AlertException exception = assertThrows(AlertException.class, () -> {
            musicService.getAllMusic(CD_GENDER);
        });

        verify(musicRepository, times(1)).findAll();
        verify(musicMapper, never()).toMusicResponseDto(any());
        verify(musicRepository).findAll();
        verify(musicMapper, never()).toMusicResponseDto(any());

        assertNotNull(exception.getMessage());
        assertEquals("Nenhuma música encontrada!", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("warn", exception.getErrorCode());
        assertThrows(AlertException.class, () -> musicService.getAllMusic(CD_GENDER));
    }

    @Test
    void validateListMusic_success() {
        when(musicRepository.findAll()).thenReturn(List.of(music));
        when(musicMapper.toMusicResponseDto(any())).thenReturn(musicResponseDto);

        List<Music> response = musicService.validateListMusic(CD_GENDER);

        verify(musicRepository, times(1)).findAll();
        verifyNoMoreInteractions(musicRepository, musicMapper);

        assertNotNull(response);
        assertEquals(CD_MUSIC, response.get(0).getCdMusic());
        assertEquals(NM_MUSIC, response.get(0).getNmMusic());
        assertEquals(SINGER, response.get(0).getSinger());
    }

    @Test
    void validateListMusic_failure() {
        when(musicRepository.findAll()).thenReturn(List.of());
        when(musicMapper.toMusicResponseDto(any())).thenReturn(musicResponseDto);

        AlertException exception = assertThrows(AlertException.class, () -> {
            musicService.validateListMusic(CD_GENDER);
        });

        verify(musicRepository, times(1)).findAll();
        verify(musicMapper, never()).toMusicResponseDto(any());
        verify(musicRepository).findAll();
        verify(musicMapper, never()).toMusicResponseDto(any());
        assertNotNull(exception.getMessage());
        assertEquals("Nenhuma música encontrada!", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("warn", exception.getErrorCode());
        assertThrows(AlertException.class, () -> musicService.validateListMusic(CD_GENDER));
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
    void updateMusic_success() {
        when(musicRepository.save(any())).thenReturn(music);
        when(musicMapper.toMusicResponseDto(any())).thenReturn(musicResponseDto);
        when(musicRepository.findById(anyLong())).thenReturn(optionalMusic);

        MusicResponseDto result = musicService.updateMusic(CD_MUSIC, musicRequestDto);

        verify(musicRepository, times(1)).findById(CD_MUSIC);
        verify(musicRepository, times(1)).save(any());
        verify(musicMapper, times(1)).toMusicResponseDto(any(Music.class));
        verifyNoMoreInteractions(musicRepository, musicMapper);

        assertNotNull(result);
        assertEquals(NM_MUSIC, result.getNmMusic());
    }

    @Test
    void updateMusic_failure() {
        when(musicRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(musicMapper.toMusicResponseDto(any())).thenReturn(musicResponseDto);

        AlertException exception = assertThrows(AlertException.class, () -> {
            musicService.updateMusic(CD_GENDER, musicRequestDto);
        });

        verify(musicRepository, times(1)).findById(CD_MUSIC);
        verify(musicMapper, never()).toMusicResponseDto(any(Music.class));
        verifyNoMoreInteractions(musicRepository, musicMapper);

        assertNotNull(exception.getMessage());
        assertEquals("Música com id 1 não cadastrada!", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("warn", exception.getErrorCode());
        assertThrows(AlertException.class, () -> musicService.updateMusic(CD_MUSIC, musicRequestDto));
    }

    @Test
    void deleteMusic_success() {
        when(musicRepository.findById(anyLong())).thenReturn(optionalMusic);

        String result = musicService.deleteMusic(CD_MUSIC);

        assertNotNull(result);
        assertEquals("Musica com ID 1 excluída com sucesso!", result);
        assertEquals(String.class, result.getClass());
    }

    @Test
    void deleteMusic_failure() {
        when(musicRepository.findById(anyLong())).thenReturn(Optional.empty());

        AlertException exception = assertThrows(AlertException.class, () -> {
            musicService.deleteMusic(CD_MUSIC);
        });

        assertNotNull(exception.getMessage());
        assertEquals("Música com id 1 não cadastrada!", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("warn", exception.getErrorCode());
        assertThrows(AlertException.class, () -> musicService.validateMusic(CD_MUSIC));
    }

    private void startMusic() {
        gender = new Gender(CD_GENDER, NM_GENDER, MUSICS, user);
        music = new Music(CD_MUSIC, NM_MUSIC, SINGER, gender);
        musicResponseDto = new MusicResponseDto(CD_MUSIC,CD_USER, NM_MUSIC, SINGER, NM_GENDER);
        musicRequestDto = new MusicRequestDto(CD_USER,NM_MUSIC, SINGER, CD_GENDER);
        optionalMusic = Optional.of(new Music(CD_MUSIC, NM_MUSIC, SINGER, gender));
        optionalGender = Optional.of(new Gender(CD_GENDER, NM_GENDER, MUSICS, user));
    }


}
