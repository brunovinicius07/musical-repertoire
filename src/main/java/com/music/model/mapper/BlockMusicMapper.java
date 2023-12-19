package com.music.model.mapper;

import com.music.model.dto.request.BlockMusicRequestDto;
import com.music.model.dto.response.BlockMusicResponseDto;
import com.music.model.entity.BlockMusic;
import com.music.model.entity.Music;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BlockMusicMapper {

    @Mapping(source = "cdUser", target = "user.cdUser")
    @Mapping(source = "cdRepertoire", target = "repertoire.cdRepertoire")
    @Mapping(source = "nmBlockMusic", target = "nmBlockMusic")
    BlockMusic toBlockMusic(BlockMusicRequestDto blockMusicRequestDto);

    @Mapping(source = "cdBlockMusic", target = "cdBlockMusic")
    @Mapping(source = "nmBlockMusic", target = "nmBlockMusic")
    @Mapping(source = "repertoire.cdRepertoire", target = "cdRepertoire")
    @Mapping(source = "user.cdUser", target = "cdUser")
    @Mapping(target = "cdMusics", expression = "java(mapMusic(blockMusic.getMusics()))")
    BlockMusicResponseDto toBlockMusicResponseDto(BlockMusic blockMusic);

    List<BlockMusicResponseDto> toListBlockMusicResponseDto(List<BlockMusic> blockMusicList);

    default List<Long> mapMusic(List<Music> musics) {
        if (musics == null) {
            return Collections.emptyList();
        }

        return musics.stream()
                .map(Music::getCdMusic)
                .collect(Collectors.toList());
    }
}
