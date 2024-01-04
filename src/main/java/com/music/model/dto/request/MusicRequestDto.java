package com.music.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicRequestDto {

    private String nmMusic;

    private String singer;

    private String letterMusic;

    private List<Long> cdBlockMusics = new ArrayList<>();

    private Long cdUser;
}
