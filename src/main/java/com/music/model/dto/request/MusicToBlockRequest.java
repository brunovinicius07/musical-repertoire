package com.music.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicToBlockRequest {

    List<Long> cdsBlockMusic;

    Long cdMusic;
}
