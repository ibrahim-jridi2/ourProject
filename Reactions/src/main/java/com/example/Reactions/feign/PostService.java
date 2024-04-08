package com.example.Reactions.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("POST")
public interface PostService {
    @GetMapping("/info/{id}")
    public ResponseEntity<PostDto> getBlog(@PathVariable Integer id);

}
