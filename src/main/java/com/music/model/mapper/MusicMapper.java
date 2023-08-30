package com.music.model.mapper;

import com.music.model.dto.request.MusicRequestDto;
import com.music.model.dto.response.MusicResponseDto;
import com.music.model.entity.Gender;
import com.music.model.entity.Music;
import com.music.repositories.GenderRepository;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MusicMapper {


    Music toMusic(MusicRequestDto musicRequestDto);


    MusicResponseDto toMusicResponseDto(Music music);

    @Mapping(source = "gender.nmGender", target = "nmGender")
    MusicResponseDto toMusicResponseDtoRegister(Music music);

}
