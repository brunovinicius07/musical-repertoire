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
    private Long cdBlockMusic;

    @Size(max = 25)
    @NotBlank
    private String nmBlockMusic;

    @ManyToOne
    @JoinColumn(name = "cdRepertoire")
    private Repertoire repertoire;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "TB_BLOCK_MUSIC_MUSIC",
            joinColumns = @JoinColumn(name = "cdBlockMusic"),
            inverseJoinColumns = @JoinColumn(name = "cdMusic")
    )
    @ToString.Exclude
    private List<Music> musics;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cdUser")
    private User user;
}
