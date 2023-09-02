package com.music.model.mapper;

import com.music.model.dto.request.GenderRequestDto;
import com.music.model.dto.response.GenderResponseDto;
import com.music.model.entity.Gender;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GenderMapper {


    Gender toGender(GenderRequestDto genderRequestDto);


    GenderResponseDto toGenderResponseDto(Gender gender);


}
