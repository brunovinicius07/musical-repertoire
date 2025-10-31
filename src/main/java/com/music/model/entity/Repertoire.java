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
@Table(name = "tb_repertoire")
public class Repertoire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdRepertoire;

    @Size(max = 30)
    @NotBlank
    private String nmRepertoire;

    @OneToMany(mappedBy = "repertoire", cascade = CascadeType.ALL)
    private List<BlockMusic> blockMusics;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cdUser")
    private User user;
}