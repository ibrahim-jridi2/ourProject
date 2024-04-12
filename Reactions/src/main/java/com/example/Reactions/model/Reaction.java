package com.example.Reactions.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer idUser;
    private ReactionType reactionType;
    private ForWho forwho;//blog wala comment
    private Integer blogid;//BLOG ID WALA COMMENT ID WALA AY 7KEYA BECH NORBETHA BIH
}
