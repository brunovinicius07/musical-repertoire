package com.music.repositories;

import com.music.model.entity.Repertoire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepertoireRepository extends JpaRepository<Repertoire, Long> {

    Optional<Repertoire> findRepertoireByNmRepertoireAndUserCdUser(String nmRepertoire, Long cdUser);

    List<Repertoire> findAllRepertoireByUserCdUser(Long cdUser);
}
