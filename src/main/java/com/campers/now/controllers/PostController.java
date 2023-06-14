package com.campers.now.controllers;

import com.campers.now.models.Post;
import com.campers.now.services.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post Management")
@RestController
@AllArgsConstructor
@RequestMapping("posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public Post add(@RequestBody Post post) {
        return postService.add(post);
    }

    @PutMapping("/{id}")
    public Post update(@RequestBody Post post, @PathVariable("id") Integer id) {
        post.setId(id);
        return postService.update(post);
    }

    @GetMapping("/{id}")
    public Post getOne(@PathVariable("id") Integer id) {
        return postService.getById(id);
    }

    @GetMapping
    public List<Post> getAll(@RequestParam(value = "page", required = false) Integer page,
                             @RequestParam(value = "sort", required = false) String sort,
                             @RequestParam(value = "dir", required = false) String dir) {
        Sort.Direction sortDir = Sort.Direction.fromString(StringUtils.hasText(dir) ? dir.toUpperCase() : Sort.Direction.ASC.name());
        return postService.getAll(page, sort, sortDir);
    }

}
