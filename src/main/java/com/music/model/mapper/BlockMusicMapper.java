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

    @Mapping(source = "idUser", target = "user.idUser")
    @Mapping(source = "idRepertoire", target = "repertoire.idRepertoire")
    @Mapping(source = "nameBlockMusic", target = "nameBlockMusic")
    BlockMusic toBlockMusic(BlockMusicRequestDto blockMusicRequestDto);

    @Mapping(source = "idBlockMusic", target = "idBlockMusic")
    @Mapping(source = "nameBlockMusic", target = "nameBlockMusic")
    @Mapping(source = "repertoire.idRepertoire", target = "idRepertoire")
    @Mapping(source = "user.idUser", target = "idUser")
    @Mapping(target = "idMusics", expression = "java(mapMusic(blockMusic.getMusics()))")
    BlockMusicResponseDto toBlockMusicResponseDto(BlockMusic blockMusic);

    List<BlockMusicResponseDto> toListBlockMusicResponseDto(List<BlockMusic> blockMusicList);

    default List<Long> mapMusic(List<Music> musics) {
        if (musics == null) {
            return Collections.emptyList();
        }

        return musics.stream()
                .map(Music::getIdMusic)
                .collect(Collectors.toList());
    }
}
