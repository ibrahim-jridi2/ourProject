package com.example.Reactions.repository;

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
    List<Reaction> findAllByIdUserAndReactionType(Integer idUser, ReactionType reactionType);

    List<Reaction> findAllByBlogid(Integer blogid);

    @Query("SELECT r.reactionType, COUNT(r) FROM Reaction r WHERE r.blogid = :blogid GROUP BY r.reactionType")
    List<Object[]> countReactionsByBlogId(@Param("blogid") Integer blogid);

    Optional<Reaction> getReactionsByBlogidAndAndIdUser(Integer blogid, Integer idUser);



}
