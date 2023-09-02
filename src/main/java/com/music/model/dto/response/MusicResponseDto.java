package com.music.model.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicResponseDto {

    @NotBlank
    private Long cdMusic;

    @NotBlank
    private String nmMusic;

    @NotBlank
    private String singer;

    @NotNull
    private String nmGender;
}
