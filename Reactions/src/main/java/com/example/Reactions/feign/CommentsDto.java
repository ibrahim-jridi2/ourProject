package com.example.Reactions.feign;

import lombok.Data;

import java.util.Date;

@Data
public class CommentsDto {
    private Integer id;
    private Integer iduser;
    private Integer blogId;
    private String content;
    private Date commentedAt;
    private Integer parentCommentId;
}
