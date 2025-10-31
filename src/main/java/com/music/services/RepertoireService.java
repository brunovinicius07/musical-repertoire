package com.music.services;

import com.music.model.dto.request.RepertoireRequestDto;
import com.music.model.dto.response.RepertoireResponseDto;
import com.music.model.entity.Repertoire;

import java.util.List;

public interface RepertoireService {
    RepertoireResponseDto registerRepertoire(RepertoireRequestDto repertoireRequestDto);

    void existingRepertoire(String nmRepertoire, Long cdUser);

    List<RepertoireResponseDto> getAllRepertoireByCdUser(Long cdUser);

    RepertoireResponseDto getRepertoireByCdRepertoire(Long cdRepertoire);

    Repertoire validateRepertoire(Long cdRepertoire);

    RepertoireResponseDto updateRepertoire(Long cdRepertoire, RepertoireRequestDto repertoireRequestDto);

    String deleteRepertoire(Long cdRepertoire);
}
