package com.music.model.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Embeddable
public class BelongingPK {

    @ManyToOne
    @JoinColumn(name = "music_cd")
    private Music music;

    @ManyToOne
    @JoinColumn(name = "gender_cd")
    private Gender gender;

}
