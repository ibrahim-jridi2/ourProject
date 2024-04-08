package com.example.Reactions.feign;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

public class PostDto {
    public class Post {
        private Integer id;
        private Integer iduser;
        private Integer comments_id ;
        private List<Integer> reactions_id;
    }
}
