package com.music.services.impl;

import com.music.exception.AlertException;
import com.music.model.dto.request.GenderRequestDto;
import com.music.model.dto.response.GenderResponseDto;
import com.music.model.entity.Gender;
import com.music.model.entity.Music;
import com.music.model.mapper.GenderMapper;
import com.music.repositories.GenderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class GenderServiceImplTest {

    public static final long CD_GENDER = 1L;
    public static final String NM_GENDER = "MPB";

    public static final List<Music> MUSICS = new ArrayList<>();

    @Autowired
    private GenderServiceImpl genderServiceImpl;

    @MockBean
    private GenderRequestDto genderRequestDto;

    @MockBean
    private GenderRepository genderRepository;

    @MockBean
    private GenderMapper genderMapper;

    private GenderResponseDto genderResponseDto;

    private Optional<Gender> genderOptional;
    private Gender gender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startGender();
    }

    @Test
    void registerGender_success() {
        when(genderRepository.save(any())).thenReturn(gender);
        when(genderMapper.toGenderResponseDto(any())).thenReturn(genderResponseDto);

        GenderResponseDto response = genderServiceImpl.registerGender(genderRequestDto);

        verify(genderRepository, times(1)).save(any());
        verify(genderMapper, times(1)).toGenderResponseDto(any());
        verify(genderMapper, times(1)).toGenderResponseDto(any(Gender.class));
        verify(genderMapper, times(1)).toGender(any());
        verify(genderMapper, times(1)).toGenderResponseDto(any());
        verifyNoMoreInteractions(genderMapper);
        verify(genderMapper).toGenderResponseDto(eq(gender));

        assertTrue(response instanceof GenderResponseDto);
        assertNotNull(response);
        assertEquals(genderResponseDto, response);
        assertEquals(CD_GENDER, response.getCdGender());
        assertEquals(NM_GENDER, response.getNmGender());
        assertEquals(MUSICS, response.getMusics());
    }

    @Test
    void registerGender_failure() {
        when(genderRepository.findByNmGender(anyString())).thenReturn(genderOptional);

        Exception exception = Assertions.assertThrows(AlertException.class, () -> {
            GenderRequestDto resquest = new GenderRequestDto(NM_GENDER);
            genderServiceImpl.registerGender(resquest);
        });

        verify(genderRepository, Mockito.times(1)).findByNmGender(NM_GENDER);
        verify(genderMapper, Mockito.never()).toGenderResponseDto(any());

        assertNotNull(exception.getMessage());
        assertEquals("Gênero MPB já está cadastrado!", exception.getMessage());
    }

    @Test
    void getAllGender() {
    }

    @Test
    void findByIdGender_success() {
        when(genderRepository.findById(Mockito.anyLong())).thenReturn(genderOptional);
        when(genderMapper.toGenderResponseDto(any())).thenReturn(genderResponseDto);
        when(genderMapper.toGender(any())).thenReturn(gender);

        GenderResponseDto result = genderServiceImpl.getGenderById(CD_GENDER);

        verify(genderRepository, Mockito.times(1)).findById(CD_GENDER);
        verify(genderMapper, Mockito.times(1)).toGenderResponseDto(gender);
        verifyNoMoreInteractions(genderRepository, genderMapper);
        verifyNoMoreInteractions(genderMapper);
        verify(genderRepository).findById(eq(CD_GENDER));
        verify(genderMapper).toGenderResponseDto(eq(gender));


        assertTrue(result instanceof GenderResponseDto);
        assertNotNull(result);
        assertEquals(genderResponseDto, result);
        assertEquals(GenderResponseDto.class, result.getClass());
        assertEquals(CD_GENDER, result.getCdGender());
        assertEquals(NM_GENDER, result.getNmGender());
        assertEquals(MUSICS, result.getMusics());
        assertDoesNotThrow(() -> genderServiceImpl.getGenderById(CD_GENDER));
    }

    @Test
    void findByIdGender_failure() {
        when(genderRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(AlertException.class, () -> {
            genderServiceImpl.getGenderById(CD_GENDER);
        });

        verify(genderRepository, Mockito.times(1)).findById(CD_GENDER);
        verify(genderMapper, Mockito.never()).toGenderResponseDto(any());
        verify(genderRepository).findById(eq(CD_GENDER));
        verify(genderMapper, never()).toGenderResponseDto(any());

        assertNotNull(exception.getMessage());
        assertEquals("Gênero com id 1 não cadastrado!", exception.getMessage());
        assertThrows(AlertException.class, () -> genderServiceImpl.getGenderById(CD_GENDER));
    }

    @Test
    void updateGender() {
    }

    @Test
    void deleteGender() {
    }

    @Test
    void existingGender() {
    }

    @Test
    void validateGender() {
    }

    @Test
    void validateListGender() {
    }

    private void startGender() {
        //listMusic.add(new Music(1L, "show", "Teste", new Gender()));
        gender = new Gender(CD_GENDER, NM_GENDER, MUSICS);
        genderResponseDto = new GenderResponseDto(CD_GENDER, NM_GENDER, MUSICS);
        genderOptional = Optional.of(new Gender(CD_GENDER, NM_GENDER,MUSICS));
    }
}