package com.campers.now.repositories;

import com.campers.now.models.CampingCenter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampingCenterRepository extends JpaRepository<CampingCenter, Integer> {
}
