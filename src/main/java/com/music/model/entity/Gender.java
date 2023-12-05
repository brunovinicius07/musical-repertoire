package com.music.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_gender")
public class Gender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdGender;

    @Size(max = 25)
    @NotBlank
    private String nmGender;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "TB_GENDER_MUSICS",
            joinColumns = @JoinColumn(name = "cdGender"),
            inverseJoinColumns = @JoinColumn(name = "cdMusics")
    )
    @ToString.Exclude
    private List<Music> musics =  new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "cd_user")
    @NotNull
    private User user;
}
