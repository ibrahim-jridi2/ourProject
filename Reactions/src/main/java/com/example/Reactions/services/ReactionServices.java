package com.example.Reactions.services;

import com.example.Reactions.feign.CommentsService;
import com.example.Reactions.feign.UserDTO;
import com.example.Reactions.feign.UserService;
import com.example.Reactions.model.ForWho;
import com.example.Reactions.model.Reaction;
import com.example.Reactions.model.ReactionType;
import com.example.Reactions.repository.ReactionRepository;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReactionServices {
    private ReactionRepository reactionRepository;
    private UserService userService;
    private CommentsService commentsService;

    public Reaction saveOrUpdateReaction(Reaction reaction) {
        Reaction existingReaction = reactionRepository.findByForWhoIdAndIdUser(reaction.getForWhoId(), reaction.getIdUser());

        if (existingReaction != null) {
            existingReaction.setReactionType(reaction.getReactionType()); // Update reaction type
            return reactionRepository.save(existingReaction);
        } else {
            Reaction newReaction = new Reaction();
            newReaction.setIdUser(reaction.getIdUser());
            newReaction.setForWho(reaction.getForWho());
            newReaction.setForWhoId(reaction.getForWhoId());
            newReaction.setReactionType(reaction.getReactionType());
            return reactionRepository.save(newReaction);
        }
    }

    public Reaction getReaction(Integer userid,ForWho forWho, Integer forwhoid) {
        return reactionRepository.getReactionsByForWhoAndForWhoIdAndAndIdUser(forWho,forwhoid,userid);
    }

    public void delete(Integer userid, ForWho forWho, Integer forwhoid) {
        reactionRepository.deleteById(reactionRepository.getReactionsByForWhoAndForWhoIdAndAndIdUser(forWho,forwhoid,userid).getId());
    }
}
