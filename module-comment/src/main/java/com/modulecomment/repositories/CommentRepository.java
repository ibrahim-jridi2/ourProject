package com.modulecomment.repositories;

import com.modulecomment.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
//    List<Comment> findByPostId(Integer id);
//    List<Comment> findByUserId(Integer id);
}
