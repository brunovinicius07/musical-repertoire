package com.music.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_music")
public class Music {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idMusic;

        @Size(max = 30)
        @NotBlank
        private String nameMusic;

        @Size(max = 30)
        @NotBlank
        private String singer;

        @Size(max = 7500)
        private String letterMusic;

        @ManyToMany(mappedBy = "musics", fetch = FetchType.LAZY)
        @ToString.Exclude
        private List<BlockMusic> blockMusics;

        @ManyToOne
        private User user;
}
