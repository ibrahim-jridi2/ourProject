package com.campers.now.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCommand implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"productCommands"})
    Command command;
    @ManyToOne(fetch = FetchType.EAGER)
    Product product;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int quantity;
    private float priceTotal;

}
