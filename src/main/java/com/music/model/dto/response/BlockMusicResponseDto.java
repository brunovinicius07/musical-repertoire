package com.music.model.dto.response;


import com.music.model.entity.Repertoire;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
