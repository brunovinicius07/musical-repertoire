package com.music.model.mapper;

import com.music.model.dto.request.RepertoireRequestDto;
import com.music.model.dto.response.RepertoireResponseDto;
import com.music.model.entity.BlockMusic;
import com.music.model.entity.Repertoire;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RepertoireMapper {

    @Mapping(target = "idRepertoire", ignore = true)
    @Mapping(target = "nameRepertoire", source = "nameRepertoire")
    @Mapping(target = "user.idUser", source = "idUser")
    Repertoire toRepertoire (RepertoireRequestDto repertoireRequestDto);

    @Mapping(source = "idRepertoire", target = "idRepertoire")
    @Mapping(source = "nameRepertoire", target = "nameRepertoire")
    @Mapping(target = "idBlockMusics", expression = "java(mapToBlockMusic(repertoire.getBlockMusics()))")
    @Mapping(source = "user.idUser", target = "idUser")
    RepertoireResponseDto toRepertoireResponseDto(Repertoire repertoire);

    List<RepertoireResponseDto> toListRepertoireResponseDto(List<Repertoire> repertoireList);

    default List<Long> mapToBlockMusic(List<BlockMusic> blockMusics) {
        if (blockMusics == null) {
            return Collections.emptyList();
        }

        return blockMusics.stream()
                .map(BlockMusic::getIdBlockMusic)
                .collect(Collectors.toList());
    }
}
