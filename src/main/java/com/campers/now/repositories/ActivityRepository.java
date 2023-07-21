package com.campers.now.repositories;

import com.campers.now.models.Activity;
import com.campers.now.models.CampingCenter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    List<Activity> findByActiveTrue(PageRequest pageRequest);
    List<Activity> findByActiveTrue();

    @Query("SELECT  DISTINCT a FROM Activity a JOIN a.users u WHERE u.id = :userId and a.active= true and a.isFavorite=true ")
    List<Activity> findFavoritesActivitiesByUserId(@Param("userId") Integer userId);

   /* @Query("SELECT DISTINCT a FROM Activity a RIGHT JOIN a.users u WHERE u.id <> :userId OR u.id IS NULL AND a.active = true")
    List<Activity> findNotFavoritesActivitiesForUserId(@Param("userId") Integer userId);
*/

    @Query("SELECT DISTINCT a FROM Activity a WHERE NOT EXISTS (SELECT u FROM a.users u WHERE u.id = :userId) AND a.active = true")
    List<Activity> findNotFavoritesActivitiesForUserId(@Param("userId") Integer userId);


    @Query("SELECT  c FROM CampingCenter c WHERE c.id = (SELECT a.campingCenter.id FROM  Activity a WHERE a.id = :actId) ")
    List<CampingCenter> findCampingCentersByActId(@Param("actId") Integer actId);


    @Query(value = "SELECT a.label as label,a.image as image, COUNT(r) as reservation_count " +
            "FROM Reservation r " +
            "JOIN r.activities a " +
            "GROUP BY a.id, a.label " +
            "ORDER BY COUNT(r) DESC")
    List<Object> findTop();
}




