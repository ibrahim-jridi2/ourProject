package com.example.Comments.Controller;


import com.example.Comments.model.Comments;
import com.example.Comments.services.CommentsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/Comments")
@CrossOrigin("*")
public class CommentsController {

    private CommentsService commentService;

    // Create a new comment
    @PostMapping("/add")
    public ResponseEntity<Comments> createComment(@RequestBody Comments comment) {
        Comments savedComment = commentService.save(comment);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Comments>> getCommentsByBlogId() {
        List<Comments> comments = commentService.findAllComments();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    // Get comments by blog ID
    @GetMapping("/byBlogId/{blogId}")
    public ResponseEntity<List<Comments>> getCommentsByBlogId(@PathVariable Integer blogId) {
        List<Comments> comments = commentService.findByBlogId(blogId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    // Get comments by user ID
    @GetMapping("/byUserId/{userId}")
    public ResponseEntity<List<Comments>> getCommentsByUserId(@PathVariable Integer userId) {
        List<Comments> comments = commentService.findAllByUserId(userId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    // Get replies for a particular comment
    @GetMapping("/replies/{commentId}")
    public ResponseEntity<List<Comments>> getRepliesByCommentId(@PathVariable Integer commentId) {
        List<Comments> replies = commentService.findByParentCommentId(commentId);
        return new ResponseEntity<>(replies, HttpStatus.OK);
    }

    // Get a comment by its ID
    @GetMapping("/info/{id}")
    public Comments getCommentById(@PathVariable Integer id) {
        return commentService.findById(id);
    }

    // Delete a comment by its ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer id) {
        commentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}