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

    @Mapping(target = "cdRepertoire", ignore = true)
    @Mapping(target = "nmRepertoire", source = "nmRepertoire")
    @Mapping(target = "user.cdUser", source = "cdUser")
    Repertoire toRepertoire (RepertoireRequestDto repertoireRequestDto);

    @Mapping(source = "cdRepertoire", target = "cdRepertoire")
    @Mapping(source = "nmRepertoire", target = "nmRepertoire")
    @Mapping(target = "cdBlockMusics", expression = "java(mapToBlockMusic(repertoire.getBlockMusics()))")
    @Mapping(source = "user.cdUser", target = "cdUser")
    RepertoireResponseDto toRepertoireResponseDto(Repertoire repertoire);

    List<RepertoireResponseDto> toListRepertoireResponseDto(List<Repertoire> repertoireList);

    default List<Long> mapToBlockMusic(List<BlockMusic> blockMusics) {
        if (blockMusics == null) {
            return Collections.emptyList();
        }

        return blockMusics.stream()
                .map(BlockMusic::getCdBlockMusic)
                .collect(Collectors.toList());
    }
}
