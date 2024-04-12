package com.example.Blog.model;
import com.example.Blog.feign.CommentsDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.*;

import java.util.Base64;
import java.util.Date;
import java.util.List;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer iduser;
    private String title;
    private Date date;
    private String description;
    private String country;
    private String address;
    private Integer comments_id ;
    @ElementCollection
    private List<Integer> reactions_id;
    @ElementCollection
    private List<String> hashtags;
    @Lob
    @Column(length = 100000)
    private byte[] pictures;



    //... other getters and setters ...


}
