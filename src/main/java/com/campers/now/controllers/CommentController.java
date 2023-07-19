package com.campers.now.controllers;

import com.campers.now.models.Comment;
import com.campers.now.services.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment Management")
@RestController
@AllArgsConstructor
@RequestMapping("comments")
@CrossOrigin(origins = "http://localhost:4200")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_CAMPER')")
    public Comment add(@RequestBody Comment comment ) {
        return commentService.add(comment);
    }

    @PutMapping("/{id}")

    public Comment update(@RequestBody Comment comment, @PathVariable("id") Integer id) {
        comment.setId(id);
        return commentService.update(comment);
    }

    @GetMapping("/{id}")
    public Comment getOne(@PathVariable("id") Integer id) {
        return commentService.getById(id);
    }

    @GetMapping("/post/{id}")
    public List<Comment> getCommentsByPost(@PathVariable("id") Integer id) {
        return commentService.getByPostId(id);
    }

    @GetMapping
    public List<Comment> getAll(@RequestParam(value = "page", required = false) Integer page,
                                @RequestParam(value = "sort", required = false) String sort,
                                @RequestParam(value = "dir", required = false) String dir) {
        Sort.Direction sortDir = Sort.Direction.fromString(StringUtils.hasText(dir) ? dir.toUpperCase() : Sort.Direction.ASC.name());
        return commentService.getAll(page, sort, sortDir);
    }
}
