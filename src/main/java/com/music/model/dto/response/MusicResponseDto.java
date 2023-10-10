package com.music.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicResponseDto {

    private Long cdMusic;

    private String nmMusic;

    private String singer;

    private String nmGender;
}
