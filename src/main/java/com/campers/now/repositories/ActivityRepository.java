package com.campers.now.repositories;

import com.campers.now.models.Activity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    List<Activity> findByActiveTrue(PageRequest pageRequest);
    List<Activity> findByActiveTrue();
}
