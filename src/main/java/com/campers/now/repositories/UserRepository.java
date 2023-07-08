package com.campers.now.repositories;

import com.campers.now.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    int countByEmail(String email);

    Optional<User> findByEmail(String username);
}
