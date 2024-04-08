package com.example.Reactions.repository;

import com.example.Reactions.model.ForWho;
import com.example.Reactions.model.Reaction;
import com.example.Reactions.model.ReactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction,Integer> {
    Reaction findByForWhoIdAndIdUser(Integer forWhoId, Integer idUser);
    Reaction getReactionsByForWhoAndForWhoIdAndAndIdUser(ForWho forWho,Integer forwhoId , Integer userid);


}
