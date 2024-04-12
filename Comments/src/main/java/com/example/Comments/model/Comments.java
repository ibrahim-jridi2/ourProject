package com.example.Comments.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer iduser;// if i was using mvc achritctor this would be a manytoone with user table
    private Integer blogId;
    private String content;
    private Date commentedAt;
    private Integer parentCommentId; // for nested replies. If it's a direct comment to the blog, this can be null.

}
