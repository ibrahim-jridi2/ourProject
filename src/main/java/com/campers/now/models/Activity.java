package com.campers.now.models;

import com.campers.now.models.enums.Season;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Activity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String label;
    private String image;
    private String description;
    private int discount;

    private float price;
    private float duration;
    private int number;

    private int capacity;
    private boolean active;
    private boolean isFavorite;

    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant modifiedAt;

    @Enumerated(EnumType.STRING)
    private Season season;

    @ManyToOne(fetch = FetchType.EAGER)
    private CampingCenter campingCenter;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "activity")
    @JsonIgnore
    private List<FeedBack> feedBacks;
    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "activity")
    @JsonIgnore
    private List<Reclamation> reclamations;*/


    @ManyToMany(mappedBy = "activities",cascade = CascadeType.REMOVE)
    private List<User> users;

}