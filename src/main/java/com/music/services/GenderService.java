package com.music.services;

import com.music.model.dto.request.GenderRequestDto;
import com.music.model.dto.response.GenderResponseDto;

import java.util.List;

public interface GenderService {
    GenderResponseDto registerGender(GenderRequestDto genderRequestDto);

    List<GenderResponseDto> getAllGender();

    GenderResponseDto getGenderById(Long cdGender);

    GenderResponseDto updateGender(Long cdGender, GenderRequestDto genderRequestDto);

    Object deleteGender(Long cdGender);
}
