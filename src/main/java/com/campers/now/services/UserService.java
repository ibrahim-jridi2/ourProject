package com.campers.now.services;

import com.campers.now.models.User;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface UserService {
    List<User> getAll(Integer pageNumber, String property, Sort.Direction direction);

    User getById(Integer id);

    User add(User o);

    User update(User o);
}
