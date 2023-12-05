package com.music.repositories;

import com.music.model.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Long> {

    Optional<Gender> findByNmGenderAndUserCdUser(String nmGender, Long cdUser);

    List<Gender> findAllGenderByUserCdUser(Long cdUser);
    List<Gender> findAllGenderByCdGender(Long cdGender);
}
