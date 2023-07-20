package com.campers.now.services.Impl;

import com.campers.now.models.Comment;
import com.campers.now.models.Post;
import com.campers.now.models.User;
import com.campers.now.repositories.CommentRepository;
import com.campers.now.repositories.PostRepository;
import com.campers.now.repositories.UserRepository;
import com.campers.now.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PostServiceImpl implements PostService {



    private final PostRepository postRepository;
    private final UserRepository    userRepository;
    private final CommentRepository commentRepository;
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

    @Override
    public Post addPost(Post post, Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        post.setUser(user);

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
    @Override
    public List<Post> getPostsByUserMostComments(Integer id, int limit) {
        // Step 1: Retrieve User's Comments
        List<Comment> userComments = commentRepository.findByUserId(id);

        // Step 2: Count Comment Occurrences
        Map<Integer, Integer> postCommentCountMap = new HashMap<>();
        for (Comment comment : userComments) {
            int postId = comment.getPost().getId();
            postCommentCountMap.put(postId, postCommentCountMap.getOrDefault(postId, 0) + 1);
        }

        // Step 3: Sort by Comment Count
        List<Map.Entry<Integer, Integer>> sortedEntries = new ArrayList<>(postCommentCountMap.entrySet());
        sortedEntries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        // Step 4: Retrieve Posts
        List<Post> postsByCommentCount = new ArrayList<>();
        int count = 0;
        for (Map.Entry<Integer, Integer> entry : sortedEntries) {
            int postId = entry.getKey();
            Post post = postRepository.findById(postId).orElse(null);
            if (post != null) {
                postsByCommentCount.add(post);
                count++;
                if (count == limit) {
                    break;
                }
            }
        }

        return postsByCommentCount;
    }

}
