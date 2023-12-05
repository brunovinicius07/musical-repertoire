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

    private Long cdMusic;

    private long cdUser;

    private String nmMusic;

    private String singer;

    private List<String> nmGenres = new ArrayList<>();
}
