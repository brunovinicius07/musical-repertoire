package com.music.repositories;

import com.music.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository< User, Long > {

    Optional<User> findByEmail(String email);
}
