package com.example.Reactions.feign;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

public class BlogDto {
    public class Blog {
        private Integer id;
        private Integer iduser;
        private Integer comments_id ;
        private List<Integer> reactions_id;
    }
}
