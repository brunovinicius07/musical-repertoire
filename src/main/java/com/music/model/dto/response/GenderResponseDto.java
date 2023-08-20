package com.music.model.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenderResponseDto {

    @NotBlank
    private Long cdGender;

    @NotBlank
    private String nmGender;
}
