package com.music.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicRequestDto {

    private String nameMusic;

    private String singer;

    private String letterMusic;

    private List<Long> idBlockMusics = new ArrayList<>();

    private Long idUser;
}
