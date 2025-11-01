package com.music.model.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockMusicResponseDto {

    private Long idBlockMusic;

    private String nameBlockMusic;

    private Long idRepertoire;

    private List<Long> idMusics = new ArrayList<>();

    private Long idUser;
}
