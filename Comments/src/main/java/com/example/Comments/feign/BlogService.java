package com.example.Comments.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@FeignClient(value = "BLOG",url = "http://localhost:8085/Blog")
public interface BlogService {
    @GetMapping("/")
    List<Blog> Blogs();
    @GetMapping("/info/{id}")
    Blog retrieveBlog(@PathVariable Integer id);
}
