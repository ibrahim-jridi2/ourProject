package com.example.Reactions.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("BLOG")
public interface BlogService {
    @GetMapping("/info/{id}")
    public ResponseEntity<BlogDto> getBlog(@PathVariable Integer id);

}
