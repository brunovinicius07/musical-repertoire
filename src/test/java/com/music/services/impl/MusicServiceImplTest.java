package com.music.services.impl;

import com.music.model.dto.request.MusicRequestDto;
import com.music.model.dto.response.MusicResponseDto;
import com.music.model.entity.BlockMusic;
import com.music.model.entity.Music;
import com.music.model.exceptions.music.MusicIsPresentException;
import com.music.model.exceptions.music.MusicNotFoundException;
import com.music.model.mapper.MusicMapper;
import com.music.repositories.BlockMusicRepository;
import com.music.repositories.MusicRepository;
import com.music.services.BlockMusicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MusicServiceImplTest {

    @Mock
    private MusicMapper musicMapper;

    @Mock
    private MusicRepository musicRepository;

    @Mock
    private BlockMusicService blockMusicService;

    @Mock
    private BlockMusicRepository blockMusicRepository;

    @InjectMocks
    private MusicServiceImpl musicService;

    private MusicRequestDto requestDto;
    private Music music;
    private MusicResponseDto responseDto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        requestDto = new MusicRequestDto(
                "Oceano",
                "Djavan",
                ".....",
                new ArrayList<>(),
                1L
        );

        music = new Music();
        music.setIdMusic(1L);
        music.setNameMusic("Oceano");
        music.setSinger("Djavan");
        music.setLetterMusic(".....");

        responseDto = new MusicResponseDto(
                1L
                , "Oceano"
                , "Djavan"
                , "....."
                , new ArrayList<>()
                , 1L);
    }

    @Test
    void shouldRegisterMusicSuccessfully_WithBlockMusics() {
        List<Long> blockIds = List.of(1L, 2L);
        requestDto.setIdBlockMusics(blockIds);

        BlockMusic block1 = new BlockMusic();
        block1.setMusics(new ArrayList<>());

        BlockMusic block2 = new BlockMusic();
        block2.setMusics(new ArrayList<>());

        List<BlockMusic> blocks = List.of(block1, block2);

        when(blockMusicService.getBlockMusicByIdBlockMusics(blockIds)).thenReturn(blocks);
        when(musicMapper.toMusic(requestDto)).thenReturn(music);
        when(musicRepository.save(music)).thenReturn(music);
        when(musicMapper.toMusicResponseDto(music)).thenReturn(responseDto);

        MusicResponseDto result = musicService.registerMusic(requestDto);

        assertNotNull(result);
        verify(musicRepository).save(music);
        verify(blockMusicService).getBlockMusicByIdBlockMusics(blockIds);

        assertTrue(block1.getMusics().contains(music));
        assertTrue(block2.getMusics().contains(music));
    }

    @Test
    void shouldRegisterMusic_WhenIdBlockMusicsIsNull() {
        requestDto.setIdBlockMusics(null);
        when(musicMapper.toMusic(requestDto)).thenReturn(music);
        when(musicRepository.save(any())).thenReturn(music);
        when(musicMapper.toMusicResponseDto(any())).thenReturn(responseDto);

        MusicResponseDto result = musicService.registerMusic(requestDto);

        assertNotNull(result);
        verify(musicRepository).save(any());
        verify(blockMusicService, never()).getBlockMusicByIdBlockMusics(any());
    }

    @Test
    void shouldRegisterMusic_WhenIdBlockMusicsIsEmpty() {
        requestDto.setIdBlockMusics(new ArrayList<>());
        when(musicMapper.toMusic(requestDto)).thenReturn(music);
        when(musicRepository.save(any())).thenReturn(music);
        when(musicMapper.toMusicResponseDto(any())).thenReturn(responseDto);

        MusicResponseDto result = musicService.registerMusic(requestDto);

        assertNotNull(result);
        verify(musicRepository).save(any());
        verify(blockMusicService, never()).getBlockMusicByIdBlockMusics(any());
    }

    @Test
    void shouldThrowException_WhenMusicAlreadyExists() {
        when(musicRepository.findByNameMusicAndSingerAndUserIdUser("Oceano", "Djavan", 1L))
                .thenReturn(Optional.of(music));

        assertThrows(MusicIsPresentException.class, () ->
                musicService.existingMusic("Oceano", "Djavan", 1L));
    }

    @Test
    void shouldGetMusicByIdSuccessfully() {
        when(musicRepository.findById(1L)).thenReturn(Optional.of(music));
        when(musicMapper.toMusicResponseDto(music)).thenReturn(responseDto);

        MusicResponseDto result = musicService.getMusicById(1L);

        assertEquals(responseDto, result);
    }

    @Test
    void shouldThrowException_WhenMusicNotFoundById() {
        when(musicRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(MusicNotFoundException.class, () -> musicService.getMusicById(99L));
    }

    @Test
    void shouldReturnAllMusicByUser() {
        when(musicRepository.findAllMusicByUserIdUser(1L)).thenReturn(List.of(music));
        when(musicMapper.toListMusicResponseDto(anyList())).thenReturn(List.of(responseDto));

        List<MusicResponseDto> list = musicService.getAllMusicByIdUser(1L);

        assertEquals(1, list.size());
    }

    @Test
    void shouldThrowException_WhenUserHasNoMusic() {
        when(musicRepository.findAllMusicByUserIdUser(1L)).thenReturn(Collections.emptyList());
        assertThrows(MusicNotFoundException.class, () -> musicService.getAllMusicByIdUser(1L));
    }

    @Test
    void shouldReturnAllMusicByBlock() {
        when(musicRepository.findAllMusicByBlockMusicsIdBlockMusic(1L)).thenReturn(List.of(music));
        when(musicMapper.toListMusicResponseDto(anyList())).thenReturn(List.of(responseDto));

        List<MusicResponseDto> list = musicService.getAllMusicByIdBlockMusic(1L);

        assertEquals(1, list.size());
    }

    @Test
    void shouldThrowException_WhenBlockHasNoMusic() {
        when(musicRepository.findAllMusicByBlockMusicsIdBlockMusic(1L)).thenReturn(Collections.emptyList());
        assertThrows(MusicNotFoundException.class, () -> musicService.getAllMusicByIdBlockMusic(1L));
    }

    @Test
    void shouldUpdateMusicSuccessfully() {
        when(musicRepository.findById(1L)).thenReturn(Optional.of(music));
        when(musicRepository.save(any())).thenReturn(music);
        when(musicMapper.toMusicResponseDto(any())).thenReturn(responseDto);

        MusicResponseDto result = musicService.updateMusic(1L, requestDto);

        assertNotNull(result);
        verify(musicRepository).save(any());
    }

    @Test
    void shouldDeleteMusic_WhenBlockMusicsIsNull() {
        music.setBlockMusics(null);
        when(musicRepository.findById(1L)).thenReturn(Optional.of(music));

        String result = musicService.deleteMusic(1L);

        assertEquals("Música com ID 1 excluída com sucesso!", result);
        verify(musicRepository).delete(music);
    }

    @Test
    void shouldDeleteMusic_WhenBlockMusicsIsEmpty() {
        music.setBlockMusics(new ArrayList<>());
        when(musicRepository.findById(1L)).thenReturn(Optional.of(music));

        String result = musicService.deleteMusic(1L);

        assertEquals("Música com ID 1 excluída com sucesso!", result);
        verify(musicRepository).delete(music);
    }

    @Test
    void shouldDeleteMusic_WhenBlockMusicsIsNotEmpty() {
        BlockMusic blockMusic = new BlockMusic();
        blockMusic.setMusics(new ArrayList<>(List.of(music)));
        music.setBlockMusics(List.of(blockMusic));

        when(musicRepository.findById(1L)).thenReturn(Optional.of(music));

        String result = musicService.deleteMusic(1L);

        assertEquals("Música com ID 1 excluída com sucesso!", result);
        verify(blockMusicRepository).save(blockMusic);
        verify(musicRepository).delete(music);
    }
}
