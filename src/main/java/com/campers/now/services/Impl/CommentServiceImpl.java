package com.campers.now.services.Impl;

import com.campers.now.models.Comment;
import com.campers.now.repositories.CommentRepository;
import com.campers.now.services.CommentService;
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
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> getAll(Integer pageNumber, String property, Sort.Direction direction) {
        if (pageNumber == null)
                return commentRepository.findAll();
        return commentRepository.findAll(
                PageRequest.of((pageNumber <= 0 ? 1 : pageNumber) - 1, 10, Sort.by(
                        List.of(Sort.Order.by(StringUtils.hasText(property) ? property : "id").with(direction)))
                )
        ).stream().collect(Collectors.toUnmodifiableList());
    }

    public Comment getById(Integer id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Comment> getByPostId(Integer id) {
        return commentRepository.findByPostId(id);
    }

    @Override
    @Transactional
    public Comment add(Comment comment) {

        return commentRepository.save(comment);

    }

    public Comment update(Comment comment) {
        getById(comment.getId());
        try {
            return commentRepository.save(comment);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
