package com.campers.now.models;

import com.campers.now.models.enums.VendingType;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String matricule;
    private String name;
    private String description;
    private String image;
    private int discount;
    private float price;
    private int stock;
    @Enumerated(EnumType.STRING)
    private VendingType vendingType;
    private boolean active;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant modifiedAt;
    @NotNull
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private User vendor;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    @JsonIgnore
    private List<FeedBack> feedBacks;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<ProductCommand> productCommands;
}