package com.music.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepertoireRequestDto {

    private String nameRepertoire;

    private Long idUser;

}
