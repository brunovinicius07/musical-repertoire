package com.music.model.mapper;

import com.music.model.dto.request.MusicRequestDto;
import com.music.model.dto.response.MusicResponseDto;
import com.music.model.entity.BlockMusic;
import com.music.model.entity.Music;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MusicMapper {

    @Mapping(target = "user.cdUser", source = "cdUser")
    Music toMusic(MusicRequestDto musicRequestDto);

    @Mapping(target = "letterMusic", source = "letterMusic")
    @Mapping(target = "nmMusic", source = "nmMusic")
    @Mapping(target = "singer", source = "singer")
    @Mapping(target = "cdMusic", source = "cdMusic")
    @Mapping(target = "cdBlockMusics", expression = "java(mapBlockMusic(music.getBlockMusics()))")
    @Mapping(target = "cdUser", source = "user.cdUser")
    MusicResponseDto toMusicResponseDto(Music music);

    List<MusicResponseDto> toListMusicResponseDto(List<Music> musicList);


    default List<Long> mapBlockMusic(List<BlockMusic> blockMusics) {
        if (blockMusics != null) {
            return blockMusics.stream()
                    .map(BlockMusic::getCdBlockMusic)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}
