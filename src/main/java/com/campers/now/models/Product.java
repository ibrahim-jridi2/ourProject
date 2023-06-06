package com.campers.now.models;

import com.campers.now.models.enums.VendingType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String matricule;
    private int discount;
    private float price;
    private int stock;
    @Enumerated(EnumType.STRING)
    private VendingType vendingType;
    private boolean isActive;
    @Temporal(TemporalType.DATE)
    private Date createdAt = new Date();
    @Temporal(TemporalType.DATE)
    private Date modifiedAt;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private User vendor;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    @JsonIgnore
    private List<FeedBack> feedBacks;
    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    @JsonIgnore
    private List<Reclamation> reclamations;*/

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<ProductCommand> productCommands;
}