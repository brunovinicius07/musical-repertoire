package com.music.model.dto.request;

import com.music.model.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicRequestDto {

    private long cdUser;

    private String nmMusic;

    private String singer;

    private List<Long> cdGenres = new ArrayList<>();
}
