package com.example.AppUser.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AppUser.models.ApplicationUser;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, Integer> {
	Optional<ApplicationUser> findByUsername(String username);
	ApplicationUser getByUsername(String username);

	boolean existsByUsername(String email);
	List<ApplicationUser> findByAuthorities_Authority(String roleName);

	ApplicationUser getByEmail(String s);
}
