package com.campers.now.services;

import com.campers.now.models.Comment;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CommentService {


    List<Comment> getAll(Integer pageNumber, String property, Sort.Direction direction);

    Comment getById(Integer id);
    List<Comment> getByPostId(Integer id);

    Comment add(Comment o);
    Comment update(Comment o);
}
