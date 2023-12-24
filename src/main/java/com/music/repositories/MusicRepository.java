package com.music.repositories;

import com.music.model.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {


    Optional<Music> findByNmMusicAndSingerAndUserCdUser(String nmMusic, String singer, Long cdUser);

    List<Music> findAllMusicByUserCdUser(Long cdUser);

    List<Music> findAllMusicByBlockMusicsCdBlockMusic(Long cdBlockMusic);
}
