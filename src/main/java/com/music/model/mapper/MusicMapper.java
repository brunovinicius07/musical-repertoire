package com.music.model.mapper;

import com.music.model.dto.request.MusicRequestDto;
import com.music.model.dto.response.GenderResponseDto;
import com.music.model.dto.response.MusicResponseDto;
import com.music.model.entity.Gender;
import com.music.model.entity.Music;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MusicMapper {

    Music toMusic(MusicRequestDto musicRequestDto);



    @Mapping(source = "user.cdUser", target = "cdUser")
    @Mapping(target = "nmGenres", expression = "java(mapGenderNames(music.getGenres()))")
    MusicResponseDto toMusicResponseDto(Music music);

    default List<String> mapGenderNames(List<Gender> genderList) {
        List<String> genderNames  = new ArrayList<>();
        if (genderList.isEmpty()) {
            return new ArrayList<>();
        }

        for (Gender gender: genderList) {
            genderNames.add(gender.getNmGender());
        }

        return genderNames;
    }

}
