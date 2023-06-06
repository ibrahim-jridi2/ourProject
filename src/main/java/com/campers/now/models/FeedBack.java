package com.campers.now.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedBack implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String label;
    private String description;
    private int rating;
    private int likes;
    private int dislikes;
    private boolean isActive;
    @Temporal(TemporalType.DATE)
    private Date createdAt = new Date();
    @Temporal(TemporalType.DATE)
    private Date modifiedAt;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private CampingCenter campingCenter;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    private Activity activity;
}