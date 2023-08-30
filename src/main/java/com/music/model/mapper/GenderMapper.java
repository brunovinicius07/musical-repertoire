package com.music.model.mapper;

import com.music.model.dto.request.GenderRequestDto;
import com.music.model.dto.response.GenderResponseDto;
import com.music.model.entity.Gender;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenderMapper {


    Gender toGender(GenderRequestDto genderRequestDto);

    //@Mapping(target = "musics", ignore = true)
    GenderResponseDto toGenderResponseDto(Gender gender);


}
