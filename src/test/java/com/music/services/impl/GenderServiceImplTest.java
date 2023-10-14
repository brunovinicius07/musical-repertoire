package com.music.services.impl;

import com.music.exception.AlertException;
import com.music.model.dto.request.GenderRequestDto;
import com.music.model.dto.response.GenderResponseDto;
import com.music.model.entity.Gender;
import com.music.model.entity.Music;
import com.music.model.mapper.GenderMapper;
import com.music.repositories.GenderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

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
        openMocks(this);
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
        verify(genderMapper).toGenderResponseDto(gender);
        verifyNoMoreInteractions(genderMapper);

        assertNotNull(response);
        assertEquals(genderResponseDto, response);
        assertEquals(CD_GENDER, response.getCdGender());
        assertEquals(NM_GENDER, response.getNmGender());
        assertEquals(MUSICS, response.getMusics());
    }

    @Test
    void registerGender_failure() {
        when(genderRepository.findByNmGender(anyString())).thenReturn(genderOptional);
        when(genderMapper.toGenderResponseDto(any())).thenReturn(genderResponseDto);

        AlertException exception = assertThrows(AlertException.class, () -> {
            GenderRequestDto resquest = new GenderRequestDto(NM_GENDER);
            genderServiceImpl.registerGender(resquest);
        });

        verify(genderRepository, times(1)).findByNmGender(NM_GENDER);
        verify(genderMapper, never()).toGenderResponseDto(any());

        assertNotNull(exception.getMessage());
        assertEquals("Gênero MPB já está cadastrado!", exception.getMessage());
    }

    @Test
    void existingGender_success() {
        when(genderRepository.findByNmGender(anyString())).thenReturn(Optional.empty());
        when(genderMapper.toGenderResponseDto(any())).thenReturn(genderResponseDto);

        genderServiceImpl.existingGender(NM_GENDER);

        verify(genderRepository, times(1)).findByNmGender(NM_GENDER);
        verifyNoMoreInteractions(genderRepository, genderMapper);
        verify(genderRepository, times(1)).findByNmGender(NM_GENDER);
        verify(genderMapper, never()).toGenderResponseDto(any());
        verifyNoMoreInteractions(genderRepository);
        verifyNoMoreInteractions(genderMapper);
    }

    @Test
    void existingGender_failure() {
        when(genderRepository.findByNmGender(NM_GENDER)).thenReturn(genderOptional);
        when(genderMapper.toGenderResponseDto(any())).thenReturn(genderResponseDto);

        AlertException exception = assertThrows(AlertException.class, () -> {
            genderServiceImpl.existingGender(NM_GENDER);
        });
        verify(genderRepository, times(1)).findByNmGender(NM_GENDER);
        verifyNoMoreInteractions(genderRepository, genderMapper);
        verify(genderRepository, times(1)).findByNmGender(NM_GENDER);
        verify(genderMapper, never()).toGenderResponseDto(any());
        verifyNoMoreInteractions(genderRepository);
        verifyNoMoreInteractions(genderMapper);

        assertNotNull(exception.getMessage());
        assertEquals("Gênero MPB já está cadastrado!", exception.getMessage());
    }

    @Test
    void getAllGender_success() {
        when(genderRepository.findAll()).thenReturn(List.of(gender));
        when(genderMapper.toGenderResponseDto(any())).thenReturn(genderResponseDto);

        List<GenderResponseDto> response = genderServiceImpl.getAllGender();

        verify(genderRepository, times(1)).findAll();
        verify(genderMapper, times(1)).toGenderResponseDto(any());
        verifyNoMoreInteractions(genderRepository, genderMapper);

        assertNotNull(response);
        assertEquals(CD_GENDER, response.get(0).getCdGender());
        assertEquals(NM_GENDER, response.get(0).getNmGender());
        assertEquals(MUSICS, response.get(0).getMusics());
    }

    @Test
    void getAllGender_failure() {
        when(genderRepository.findAll()).thenReturn(List.of());
        when(genderMapper.toGenderResponseDto(any())).thenReturn(genderResponseDto);


        AlertException exception = assertThrows(AlertException.class, () -> {
            genderServiceImpl.getAllGender();
        });

        verify(genderRepository, times(1)).findAll();
        verify(genderMapper, never()).toGenderResponseDto(any());
        verify(genderRepository).findAll();
        verify(genderMapper, never()).toGenderResponseDto(any());

        assertNotNull(exception.getMessage());
        assertEquals("Nenhum gênero encontrado!", exception.getMessage());
        assertThrows(AlertException.class, () -> genderServiceImpl.getAllGender());
    }

    @Test
    void validateListGender_success() {
        when(genderRepository.findAll()).thenReturn(List.of(gender));
        when(genderMapper.toGenderResponseDto(any())).thenReturn(genderResponseDto);

        List<Gender> response = genderServiceImpl.validateListGender();

        verify(genderRepository, times(1)).findAll();
        verifyNoMoreInteractions(genderRepository, genderMapper);

        assertNotNull(response);
        assertEquals(CD_GENDER, response.get(0).getCdGender());
        assertEquals(NM_GENDER, response.get(0).getNmGender());
        assertEquals(MUSICS, response.get(0).getMusics());
    }

    @Test
    void validateListGender_failure() {
        when(genderRepository.findAll()).thenReturn(List.of());
        when(genderMapper.toGenderResponseDto(any())).thenReturn(genderResponseDto);

        AlertException exception = assertThrows(AlertException.class, () -> {
            genderServiceImpl.validateListGender();
        });

        verify(genderRepository, times(1)).findAll();
        verify(genderMapper, never()).toGenderResponseDto(any());
        verify(genderRepository).findAll();
        verify(genderMapper, never()).toGenderResponseDto(any());
        assertNotNull(exception.getMessage());
        assertEquals("Nenhum gênero encontrado!", exception.getMessage());
        assertThrows(AlertException.class, () -> genderServiceImpl.getAllGender());
    }

    @Test
    void findByIdGender_success() {
        when(genderRepository.findById(anyLong())).thenReturn(genderOptional);
        when(genderMapper.toGenderResponseDto(any())).thenReturn(genderResponseDto);
        when(genderMapper.toGender(any())).thenReturn(gender);

        GenderResponseDto result = genderServiceImpl.getGenderById(CD_GENDER);

        verify(genderRepository, times(1)).findById(CD_GENDER);
        verify(genderMapper, times(1)).toGenderResponseDto(gender);
        verifyNoMoreInteractions(genderRepository, genderMapper);
        verifyNoMoreInteractions(genderMapper);
        verify(genderRepository).findById(CD_GENDER);
        verify(genderMapper).toGenderResponseDto(gender);

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
        when(genderRepository.findById(anyLong())).thenReturn(Optional.empty());

        AlertException exception = assertThrows(AlertException.class, () -> {
            genderServiceImpl.getGenderById(CD_GENDER);
        });

        verify(genderRepository, times(1)).findById(CD_GENDER);
        verify(genderMapper, never()).toGenderResponseDto(any());
        verify(genderRepository).findById(CD_GENDER);
        verify(genderMapper, never()).toGenderResponseDto(any());

        assertNotNull(exception.getMessage());
        assertEquals("Gênero com id 1 não cadastrado!", exception.getMessage());
        assertThrows(AlertException.class, () -> genderServiceImpl.getGenderById(CD_GENDER));
    }

    @Test
    void validateGender_success() {
        when(genderRepository.findById(anyLong())).thenReturn(genderOptional);
        when(genderMapper.toGenderResponseDto(any())).thenReturn(genderResponseDto);
        when(genderMapper.toGender(any())).thenReturn(gender);

        Gender result = genderServiceImpl.validateGender(CD_GENDER);

        verify(genderRepository, times(1)).findById(CD_GENDER);
        verifyNoMoreInteractions(genderRepository, genderMapper);
        verify(genderMapper, never()).toGenderResponseDto(any());
        verify(genderRepository).findById(CD_GENDER);

        assertNotNull(result);
        assertEquals(gender, result);
        assertEquals(CD_GENDER, result.getCdGender());
        assertEquals(NM_GENDER, result.getNmGender());
        assertEquals(MUSICS, result.getMusics());
        assertDoesNotThrow(() -> genderServiceImpl.getGenderById(CD_GENDER));
        assertDoesNotThrow(() -> genderServiceImpl.validateGender(CD_GENDER));
    }

    @Test
    void validateGender_failure() {
        when(genderRepository.findById(anyLong())).thenReturn(Optional.empty());

        AlertException exception = assertThrows(AlertException.class, () -> {
            genderServiceImpl.validateGender(CD_GENDER);
        });

        verify(genderRepository, times(1)).findById(CD_GENDER);
        verify(genderMapper, never()).toGenderResponseDto(any());
        verify(genderRepository).findById(CD_GENDER);
        verify(genderMapper, never()).toGenderResponseDto(any());

        assertNotNull(exception.getMessage());
        assertEquals("Gênero com id 1 não cadastrado!", exception.getMessage());
        assertThrows(AlertException.class, () -> genderServiceImpl.validateGender(CD_GENDER));
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
        genderOptional = Optional.of(new Gender(CD_GENDER, NM_GENDER, MUSICS));
    }
}