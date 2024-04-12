package com.example.Comments.services;

import com.example.Comments.feign.BlogService;
import com.example.Comments.model.Comments;
import com.example.Comments.repository.CommentRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class CommentsService {

    // private final BlogService blogService;

    private final CommentRepository commentRepository;

    public Comments save(Comments comment) {
        //   List<Integer> blogs=blogService.retrieveBlog(comment.getBlogId()).getBody();
        return commentRepository.save(comment);
    }

    public List<Comments> findByBlogId(Integer blogId) {
        return commentRepository.findByBlogId(blogId);
    }

    public List<Comments> findAllByUserId(Integer iduser) {
        return commentRepository.findAllByIduser(iduser);
    }

    public List<Comments> findByParentCommentId(Integer parentCommentId) {
        return commentRepository.findByParentCommentId(parentCommentId);
    }

    public Comments findById(Integer id) {
        return commentRepository.findById(id).get();
    }

    public void deleteById(Integer id) {
        commentRepository.deleteById(id);
    }

    public List<Comments> findAllComments() {
        return commentRepository.findAll();
    }
}
