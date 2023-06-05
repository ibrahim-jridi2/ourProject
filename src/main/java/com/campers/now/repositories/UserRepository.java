package com.campers.now.repositories;

import com.campers.now.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    int countByEmail(String email);
}
