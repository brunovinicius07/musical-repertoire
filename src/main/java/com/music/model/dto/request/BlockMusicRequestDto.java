package com.music.model.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockMusicRequestDto {

    private String nmBlockMusic;

    private Long cdRepertoire;

    private Long cdUser;
}
