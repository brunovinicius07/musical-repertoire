package com.music.services.impl;

import com.music.model.dto.request.GenderRequestDto;
import com.music.model.dto.response.GenderResponseDto;
import com.music.model.entity.Gender;
import com.music.model.entity.Music;
import com.music.model.mapper.GenderMapper;
import com.music.repositories.GenderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Any;
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
    void whenFindByIdThenReturnAnGenderInstance() {

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
        genderOptional = Optional.of( new Gender(CD_GENDER, NM_GENDER, new ArrayList<>()));
    }
}