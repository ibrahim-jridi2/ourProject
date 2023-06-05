package com.campers.now.repositories;

import com.campers.now.models.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReclamationRepository extends JpaRepository<Reclamation, Integer> {
}
