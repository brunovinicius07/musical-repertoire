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

    @Mapping(target = "user.idUser", source = "idUser")
    Music toMusic(MusicRequestDto musicRequestDto);

    @Mapping(target = "letterMusic", source = "letterMusic")
    @Mapping(target = "nameMusic", source = "nameMusic")
    @Mapping(target = "singer", source = "singer")
    @Mapping(target = "idMusic", source = "idMusic")
    @Mapping(target = "idBlockMusics", expression = "java(mapBlockMusic(music.getBlockMusics()))")
    @Mapping(target = "idUser", source = "user.idUser")
    MusicResponseDto toMusicResponseDto(Music music);

    List<MusicResponseDto> toListMusicResponseDto(List<Music> musicList);

    List<Music> toListMusics(List<MusicResponseDto> musicResponseDtos);


    default List<Long> mapBlockMusic(List<BlockMusic> blockMusics) {
        if (blockMusics != null) {
            return blockMusics.stream()
                    .map(BlockMusic::getIdBlockMusic)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}
