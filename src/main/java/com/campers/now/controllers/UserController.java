package com.campers.now.controllers;

import com.campers.now.DTO.UserRequest;
import com.campers.now.models.Role;
import com.campers.now.models.User;
import com.campers.now.repositories.RoleRepository;
import com.campers.now.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "User Management")
@RestController
@AllArgsConstructor
@RequestMapping("users")
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final RoleRepository roleRepository;

    @PostMapping
    @Secured("ROLE_SUPER_ADMIN")
    public User add(@RequestBody UserRequest user) {
        System.out.println(user);
        return userService.add(user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CAMPER')")
    public User update(@RequestBody UserRequest user, @PathVariable("id") Integer id) {
        user.setId(id);
        return userService.update(user);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CAMPER')")
    public User getOne(@PathVariable("id") Integer id) {
        return userService.getById(id);
    }

    @GetMapping("/recently-viewed/camping-centers/{userID}")
    public List<Map<String, Object>> getRecentlyViewedCampingCenters(@PathVariable Integer userID) {
        return userService.getRecentlyViewedCampingCenters(userID);
    }

    @GetMapping("/recently-viewed/posts/{userID}")
    public List<Map<String, Object>> getRecentlyViewedPosts(@PathVariable Integer userID) {
        return userService.getRecentlyViewedPosts(userID);
    }

    @GetMapping("/suggestions/posts")
    public List<Map<String, Object>> getSuggestedPosts(@RequestParam List<String> tags) {
        return userService.getSuggestedPosts(tags);
    }

    @GetMapping("/stats-by-season/{id}")
    public List<Map<String, Object>> getStatsBySeason(@PathVariable("id") Integer id) {
        return userService.getStatsByUserIdAndSeason(id);
    }

    @GetMapping("/stats-by-date/{id}")
    public List<Map<String, Object>> getStatsByDate(@PathVariable("id") Integer id) {
        return userService.getStatsByUserIdForEveryYearAndMonth(id);
    }

    @GetMapping
    @Secured("ROLE_SUPER_ADMIN")
    public List<User> getAll(@RequestParam(value = "page", required = false) Integer page,
                             @RequestParam(value = "sort", required = false) String sort,
                             @RequestParam(value = "dir", required = false) String dir) {

        Sort.Direction sortDir = Sort.Direction.fromString(StringUtils.hasText(dir) ? dir.toUpperCase() : Sort.Direction.ASC.name());
        return userService.getAll(page, sort, sortDir);
    }

    @GetMapping("roles")
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
