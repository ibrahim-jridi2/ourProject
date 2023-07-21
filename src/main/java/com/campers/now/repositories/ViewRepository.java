package com.campers.now.repositories;

import com.campers.now.models.View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ViewRepository extends JpaRepository<View, Integer> {

    @Query(value = "select DISTINCT c.* from campingdb.camping_center c " +
            "inner join campingdb.view v on c.id = v.entity_id " +
            "and v.entity='camping_center' " +
            "and v.user_id = :userID " +
            "order by v.viewed_at desc " +
            "limit 5", nativeQuery = true)
    List<Map<String, Object>> getRecentlyViewedCampingCenters(@Param("userID") Integer userID);

    @Query(value = "select DISTINCT p.* from campingdb.post p " +
            "inner join campingdb.view v on p.id = v.entity_id " +
            "and v.entity='post' and v.user_id = :userID " +
            "order by v.viewed_at desc " +
            "limit 6", nativeQuery = true)
    List<Map<String, Object>> getRecentlyViewedPosts(@Param("userID") Integer userID);

    @Query(value = "select DISTINCT p.* from campingdb.post p " +
            "inner join campingdb.post_tags pt on p.id = pt.post_id and pt.tags in :tags", nativeQuery = true)
    List<Map<String, Object>> getSuggestedPosts(@Param("tags") List<String> tags);

}




