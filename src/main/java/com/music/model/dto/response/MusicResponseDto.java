package com.music.model.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicResponseDto {

    @NotBlank
    private String nmMusic;

    @NotBlank
    private String singer;
}
