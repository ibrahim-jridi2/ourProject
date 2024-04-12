package com.example.Blog.repository;

import com.example.Blog.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog,Integer> {
    List<Blog> findAllByIduser(Integer iduser);

    List<Blog> findAllByHashtags(String str);
}
