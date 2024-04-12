package com.example.Reactions.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("COMMENTS")
public interface CommentsService {

    @GetMapping("/byBlogId/{blogId}")
    public ResponseEntity<List<CommentsDto>> getCommentsByBlogId(@PathVariable Integer blogId);
}
