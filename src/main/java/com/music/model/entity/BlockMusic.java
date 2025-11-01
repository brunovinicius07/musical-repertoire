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
@Table(name = "tb_block_musical")
public class BlockMusic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBlockMusic;

    @Size(max = 25)
    @NotBlank
    private String nameBlockMusic;

    @ManyToOne
    @JoinColumn(name = "idRepertoire")
    private Repertoire repertoire;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "TB_BLOCK_MUSIC_MUSIC",
            joinColumns = @JoinColumn(name = "idBlockMusic"),
            inverseJoinColumns = @JoinColumn(name = "idMusic")
    )
    @ToString.Exclude
    private List<Music> musics;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser")
    private User user;
}
