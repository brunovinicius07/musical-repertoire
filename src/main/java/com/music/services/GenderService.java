package com.music.services;

import com.music.model.dto.request.GenderRequestDto;
import com.music.model.dto.response.GenderResponseDto;

public interface GenderService {
    GenderResponseDto registerGender(GenderRequestDto genderRequestDto);
}
