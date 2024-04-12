package com.example.Blog.services;

import com.example.Blog.feign.CommentsDto;
import com.example.Blog.feign.CommentsService;
import com.example.Blog.feign.UserService;
import com.example.Blog.model.Blog;
import com.example.Blog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class Blogservice {
    private final UserService userService;
    private final BlogRepository blogRepository;
    private final CommentsService commentsService;
    public Blog createBlog(Blog blog) {
        // Validate user_id by calling the AppUser service
        // If valid, save and return, otherwise throw an exception
        return blogRepository.save(blog);
    }

    public Blog getBlogById(Integer id) {
        // Fetch the blog by ID and return
        return blogRepository.findById(id).orElse(null); // Handle not found scenario
    }

    public Blog updateBlog(Integer id, Blog blog) {
        if(blogRepository.existsById(id)) {
            return blogRepository.save(blog);
        } else {
            return null;
        }
    }

    public void deleteBlog(Integer id) {
        blogRepository.deleteById(id);
    }

    public List<Blog> getAll() {
        return blogRepository.findAll();
    }

    public List<Blog> getMyBlog(Integer id ) {
        return blogRepository.findAllByIduser(id);
    }

  /*  public List<Blog> getFullBlog() {
        List<Blog> allBlogs = getAll();

        for (Blog blog : allBlogs) {
            ResponseEntity<List<CommentsDto>> commentsResponse = commentsService.getCommentsByBlogId(blog.getId());

            if (commentsResponse.getStatusCode().equals(HttpStatus.OK)) {
                blog.setComments(commentsResponse.getBody());
            }
        }
        return allBlogs;
    }*/
    public List<String> getFullBlog2() {
        List<Blog> allBlogs = getAll();
        List<String> strings = null;
        for (Blog blog : allBlogs) {
            ResponseEntity<List<CommentsDto>> commentsResponse = commentsService.getCommentsByBlogId(blog.getId());
            String str=blog+String.valueOf(commentsResponse);
            strings.add(str);
        }
        return strings;
    }

    public List<Blog> getbyhashtag(String str ){
        return blogRepository.findAllByHashtags(str);
    }


}
