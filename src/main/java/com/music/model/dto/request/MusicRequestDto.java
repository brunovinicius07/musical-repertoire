package com.music.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicRequestDto {

    @NotBlank
    private String nmMusic;

    @NotBlank
    private String singer;

    @NotNull
    private Long cdGender;
}
