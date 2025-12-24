package com.music.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicResponseDto {

    private Long idMusic;

    private String nameMusic;

    private String singer;

    private String letterMusic;

    private List<Long> idBlockMusics;

    private Long idUser;
}
