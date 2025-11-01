package com.music.model.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockMusicRequestDto {

    private String nameBlockMusic;

    private Long idRepertoire;

    private Long idUser;
}
