package com.music.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_music")
public class Music {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long cdMusic;

        @Size(max = 30)
        @NotBlank
        private String nmMusic;

        @Size(max = 30)
        @NotBlank
        private String singer;

        @Size(max = 7500)
        private String letterMusic;

        @ManyToMany(mappedBy = "musics")
        @ToString.Exclude
        private List<BlockMusic> blockMusics;

        @ManyToOne
        private User user;
}
