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

@SpringBootTest
class GenderServiceImplTest {

    public static final long CD_GENDER = 1L;
    public static final String NM_GENDER = "MPB";

    @MockBean
    private GenderRepository genderRepository;

    @MockBean
    private GenderMapper genderMapper;

    @Autowired
    private GenderServiceImpl genderService;

    private Gender gender;
    private GenderRequestDto genderRequestDto;
    private GenderResponseDto genderResponseDto;
    private Optional<Gender> genderOptional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startGender();
    }

    @Test
    void registerGender() {
    }

    @Test
    void getAllGender() {
    }

    @Test
    void findByIdGender_success() {
        Mockito.when(genderRepository.findById(Mockito.anyLong())).thenReturn(genderOptional);
        Mockito.when(genderMapper.toGenderResponseDto(Mockito.any())).thenReturn(genderResponseDto);
        Mockito.when(genderMapper.toGender(Mockito.any())).thenReturn(gender);

        GenderResponseDto result = genderService.getGenderById(CD_GENDER);

        Mockito.verify(genderRepository, Mockito.times(1)).findById(CD_GENDER);
        Mockito.verify(genderMapper, Mockito.times(1)).toGenderResponseDto(gender);
        Mockito.verifyNoMoreInteractions(genderRepository, genderMapper);
        Mockito.verifyNoMoreInteractions(genderMapper);


        Assertions.assertNotNull(result);
        Assertions.assertEquals(genderResponseDto, result);
        Assertions.assertEquals(GenderResponseDto.class, result.getClass());
        Assertions.assertEquals(CD_GENDER, result.getCdGender());
        Assertions.assertEquals(NM_GENDER, result.getNmGender());
        Assertions.assertEquals(Collections.emptyList(), result.getMusics());
    }

    @Test
    void findByIdGender_failure() {
        Mockito.when(genderRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(AlertException.class, () -> {
            genderService.getGenderById(CD_GENDER);
        });

        Mockito.verify(genderRepository, Mockito.times(1)).findById(CD_GENDER);
        Mockito.verify(genderMapper, Mockito.never()).toGenderResponseDto(Mockito.any());

        Assertions.assertNotNull(exception.getMessage());
        Assertions.assertEquals("Genêro com id 1 não cadastrado!", exception.getMessage());
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
        List<Music> listMusic = Collections.emptyList();
        //listMusic.add(new Music(1L, "show", "Teste", new Gender()));
        gender = new Gender(CD_GENDER, NM_GENDER, new ArrayList<>());
        genderResponseDto = new GenderResponseDto(CD_GENDER, NM_GENDER, new ArrayList<>());
        genderOptional = Optional.of(new Gender(CD_GENDER, NM_GENDER, new ArrayList<>()));
    }
}