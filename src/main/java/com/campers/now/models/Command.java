package com.campers.now.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedEntityGraph(name = "commandWithProductCommands",
        attributeNodes = @NamedAttributeNode("productCommands"))
public class Command implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String shippingAddress;
    private String method;
    private boolean confirmed;
    private String customerFirstName;
    private String customerLastName;
    private String customerEmail;
    private String phoneNumber;


    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant modifiedAt;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "command", fetch = FetchType.EAGER)

    private List<ProductCommand> productCommands;
}
