package com.campers.now.services;

import com.campers.now.DTO.UserRequest;
import com.campers.now.models.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> getAll(Integer pageNumber, String property, Sort.Direction direction);

    User getById(Integer id);

    User add(UserRequest o);

    String getPasswordByEmail(String email);

    User update(UserRequest o);

    User getByEmail(String email);

    List<Map<String, Object>> getStatsByUserIdForEveryYearAndMonth(Integer userId);

    List<Map<String, Object>> getStatsByUserIdAndSeason(Integer userId);

    List<Map<String, Object>> getRecentlyViewedCampingCenters(@Param("userID") Integer userID);

    List<Map<String, Object>> getRecentlyViewedPosts(@Param("userID") Integer userID);

    List<Map<String, Object>> getSuggestedPosts(@Param("tags") List<String> tags);

}
