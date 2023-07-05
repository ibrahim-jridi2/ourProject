package com.campers.now.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampingCenter implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String label;
    private String image;
    private String description;
    private String location;
    private float price;
    private int discount;
    private int capacity;
    private int telephone;
    private boolean isActive;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant modifiedAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "campingCenter", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"campingCenter"})
    private List<Activity> activities;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "campingCenter")
    @JsonIgnore
    private List<Reservation> reservations;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "campingCenter")
    @JsonIgnore
    private List<FeedBack> feedBacks;
    /*
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "campingCenter")
    @JsonIgnore
    private List<Reclamation> reclamations;*/
}