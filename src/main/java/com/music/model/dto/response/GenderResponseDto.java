package com.music.model.dto.response;

import com.music.model.entity.Music;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenderResponseDto {

    @NotBlank
    private Long cdGender;

    @NotNull
    private String nmGender;

    @NotNull
    private List<Music> musics = new ArrayList<>();
}
