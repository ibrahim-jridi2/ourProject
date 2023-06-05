package com.campers.now.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
    private String description;
    private int discount;
    private float price;
    private float duration;
    private int number;
    private int maxNbr;
    private boolean isActive;
    @Temporal(TemporalType.DATE)
    private Date createdAt = new Date();
    @Temporal(TemporalType.DATE)
    private Date modifiedAt;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private CampingCenter campingCenter;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "activity")
    @JsonIgnore
    private List<FeedBack> feedBacks;
    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "activity")
    @JsonIgnore
    private List<Reclamation> reclamations;*/
}