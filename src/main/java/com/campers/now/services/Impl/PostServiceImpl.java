package com.campers.now.services.Impl;

import com.campers.now.models.Post;
import com.campers.now.repositories.PostRepository;
import com.campers.now.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PostServiceImpl implements PostService {


    private final PostRepository postRepository;

    public List<Post> getAll(Integer pageNumber, String property, Sort.Direction direction) {
        if (pageNumber == null)
            return postRepository.findAll();
        return postRepository.findAll(
                PageRequest.of((pageNumber <= 0 ? 1 : pageNumber) - 1, 10, Sort.by(
                        List.of(Sort.Order.by(StringUtils.hasText(property) ? property : "id").with(direction)))
                )
        ).stream().collect(Collectors.toUnmodifiableList());
    }

    public Post getById(Integer id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public Post add(Post post) {
        try {
            return postRepository.save(post);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Post update(Post post) {
        getById(post.getId());
        try {
            return postRepository.save(post);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
