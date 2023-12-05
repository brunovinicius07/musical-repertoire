package com.music.model.dto.response;

import com.music.model.entity.Music;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenderResponseDto {

    private Long cdGender;

    private Long cdUser;

    private String nmGender;

    private List<Music> musics = new ArrayList<>();
}
