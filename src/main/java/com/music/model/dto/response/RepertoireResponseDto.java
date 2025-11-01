package com.music.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepertoireResponseDto {

    private Long idRepertoire;

    private String nameRepertoire;

    private List<Long> idBlockMusics = new ArrayList<>();

    private Long idUser;
}
