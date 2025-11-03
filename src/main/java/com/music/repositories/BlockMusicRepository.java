package com.music.repositories;

import com.music.model.entity.BlockMusic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlockMusicRepository extends JpaRepository<BlockMusic, Long> {

    Optional<BlockMusic> findBlockMusicByNameBlockMusicAndRepertoireIdRepertoire(
            String nameBlockMusic, Long idRepertoire);

    List<BlockMusic> findAllBlockMusicByUserIdUser(Long idUser);

    List<BlockMusic> findAllBlockMusicByRepertoireIdRepertoire(Long idRepertoire);

}
