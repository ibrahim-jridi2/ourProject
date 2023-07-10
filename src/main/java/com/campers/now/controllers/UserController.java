package com.campers.now.controllers;

import com.campers.now.models.User;
import com.campers.now.utils.UserRequest;
import com.campers.now.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User Management")
@RestController
@AllArgsConstructor
@RequestMapping("users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    private final UserService userService;

    @PostMapping
    @Secured("ROLE_SUPER_ADMIN")
    public User add(@RequestBody UserRequest user) {
        System.out.println(user);
        return userService.add(user);
    }

    @PutMapping("/{id}")
    public User update(@RequestBody UserRequest user, @PathVariable("id") Integer id) {
        user.setId(id);
        return userService.update(user);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CAMPER')")
    public User getOne(@PathVariable("id") Integer id) {
        return userService.getById(id);
    }

    @GetMapping
    @Secured("ROLE_SUPER_ADMIN")
    public List<User> getAll(@RequestParam(value = "page", required = false) Integer page,
                            @RequestParam(value = "sort", required = false) String sort,
                            @RequestParam(value = "dir", required = false) String dir) {

        Sort.Direction sortDir = Sort.Direction.fromString(StringUtils.hasText(dir) ? dir.toUpperCase() : Sort.Direction.ASC.name());
        return userService.getAll(page, sort, sortDir);
    }
}
