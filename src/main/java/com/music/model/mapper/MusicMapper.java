package com.music.model.mapper;

import com.music.model.dto.request.MusicRequestDto;
import com.music.model.dto.response.MusicResponseDto;
import com.music.model.entity.Music;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MusicMapper {


    Music toMusic(MusicRequestDto musicRequestDto);


    @Mapping(source = "gender.nmGender", target = "nmGender")
    MusicResponseDto toMusicResponseDto(Music music);

}
