package com.campers.now.services;

import com.campers.now.models.Post;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface PostService {
    List<Post> getAll(Integer pageNumber, String property, Sort.Direction direction);

    Post getById(Integer id);

    Post add(Post o);

    Post addPost(Post post, Integer id);

    Post update(Post o);
    List<Post> getPostsByUserMostComments(Integer id, int limit);
}
