package com.music.model.mapper;

import com.music.model.dto.request.GenderRequestDto;
import com.music.model.dto.response.GenderResponseDto;
import com.music.model.entity.Gender;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GenderMapper {

    @Mapping(source = "cdUser", target = "user.cdUser")
    Gender toGender(GenderRequestDto genderRequestDto);

    @Mapping(source = "user.cdUser", target = "cdUser")
    GenderResponseDto toGenderResponseDto(Gender gender);
}
