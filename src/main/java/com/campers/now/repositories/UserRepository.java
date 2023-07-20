package com.campers.now.repositories;

import com.campers.now.models.Activity;
import com.campers.now.models.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    int countByEmail(String email);

    Optional<User> findByEmail(String username);

    @Query(value = "select password from user where email = :email",nativeQuery = true)
    String getPasswordByEmail(@Param("email") String username);

}
