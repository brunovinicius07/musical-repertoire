package com.music.services;

import com.music.model.dto.request.RepertoireRequestDto;
import com.music.model.dto.response.RepertoireResponseDto;
import com.music.model.entity.Repertoire;

import java.util.List;

public interface RepertoireService {
    RepertoireResponseDto registerRepertoire(RepertoireRequestDto repertoireRequestDto);

    List<RepertoireResponseDto> getAllRepertoireByIdUser(Long idUser);

    RepertoireResponseDto getRepertoireByIdRepertoire(Long idRepertoire);

    RepertoireResponseDto updateRepertoire(Long idRepertoire, RepertoireRequestDto repertoireRequestDto);

    String deleteRepertoire(Long idRepertoire);

    void existingRepertoire(String nameRepertoire, Long idUser);

    Repertoire validateRepertoire(Long idRepertoire);

}
