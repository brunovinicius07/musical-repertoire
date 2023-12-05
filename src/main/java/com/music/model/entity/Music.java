package com.music.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "gender")
        @ToString.Exclude
        private List<Gender> genres = new ArrayList<>();

        @ManyToOne
        @JoinColumn(name = "cd_user")
        @NotNull
        private User user;

}
