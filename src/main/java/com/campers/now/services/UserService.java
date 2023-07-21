package com.campers.now.services;

import com.campers.now.models.User;
import com.campers.now.DTO.UserRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> getAll(Integer pageNumber, String property, Sort.Direction direction);

    User getById(Integer id);
    User add(UserRequest o);

    String getPasswordByEmail(String email);

    User update(UserRequest o);

    User getByEmail(String email);

    List<Map<String, Object>> getRevenueByUserIdForEveryYearAndMonth(Integer userId);
    List<Map<String, Object>> getRevenueByUserIdAndSeason(Integer userId);
}
