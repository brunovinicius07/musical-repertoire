package com.music.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Builder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "table_music")
public class Music {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long cdMusic;

        private String nmMusic;

        private String singer;

        @JsonIgnore
        @ManyToOne
        @JoinColumn(name = "cd_gender")
        @NotNull
        private Gender gender;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Music music = (Music) o;
        return getCdMusic() != null && Objects.equals(getCdMusic(), music.getCdMusic());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }

}
