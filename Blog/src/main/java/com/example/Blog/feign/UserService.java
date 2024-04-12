package com.example.Blog.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@FeignClient(value = "USER")
public interface UserService {
    @GetMapping("/list-user")
    List<UserDTO> readItems();
    @GetMapping("/info")
    UserDTO retrieveUserInfo();

    @GetMapping("/{id}")
    UserDTO getUserById(@RequestParam("id") Integer id );

}
