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

    private Long cdBlockMusic;

    private String nmBlockMusic;

    private Long cdRepertoire;

    private List<Long> cdMusics = new ArrayList<>();

    private Long cdUser;
}
