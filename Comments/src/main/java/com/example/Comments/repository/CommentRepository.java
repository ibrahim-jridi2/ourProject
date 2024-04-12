package com.example.Comments.repository;


import com.example.Comments.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comments,Integer> {
    List<Comments> findByBlogId(Integer blogId); // to get all comments for a particular blog.

    List<Comments> findAllByIduser(Integer iduser); // to get all replies for a particular comment.
    List<Comments> findByParentCommentId(Integer parentCommentId);

}