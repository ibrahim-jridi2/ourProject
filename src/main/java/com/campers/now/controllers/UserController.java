package com.campers.now.controllers;

import com.campers.now.models.User;
import com.campers.now.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User Management")
@RestController
@AllArgsConstructor
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public User add(@RequestBody User user) {
        return userService.add(user);
    }

    @PutMapping("/{id}")
    public User update(@RequestBody User user, @PathVariable("id") Integer id) {
        user.setId(id);
        return userService.update(user);
    }

    @GetMapping("/{id}")
    public User getOne(@PathVariable("id") Integer id) {
        return userService.getById(id);
    }

    @GetMapping
    public List<User> getAll(Integer page, String sort, String dir) {

        Sort.Direction sortDir = Sort.Direction.fromString(StringUtils.hasText(dir) ? dir.toUpperCase() : Sort.Direction.ASC.name());
        return userService.getAll(page, sort, sortDir);
    }
}