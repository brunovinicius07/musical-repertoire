package com.music.model.mapper;

import com.music.model.dto.request.MusicRequestDto;
import com.music.model.dto.response.MusicResponseDto;
import com.music.model.entity.Music;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MusicMapper {

    @Mapping(source = "cdGender", target = "gender.nmGender")
    @Mapping(source = "cdUser", target = "gender.user.cdUser")
    Music toMusic(MusicRequestDto musicRequestDto);


    @Mapping(source = "gender.nmGender", target = "nmGender")
    @Mapping(source = "gender.user.cdUser", target = "cdUser")
    MusicResponseDto toMusicResponseDto(Music music);

}
