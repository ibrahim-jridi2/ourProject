package com.campers.now.repositories;

import com.campers.now.models.Activity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    List<Activity> findByActiveTrue(PageRequest pageRequest);
    List<Activity> findByActiveTrue();

    @Query("SELECT a FROM Activity a JOIN a.users u WHERE u.id = :userId")
    List<Activity> findActivitiesByUserId(@Param("userId") Integer userId);

}
