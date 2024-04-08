package com.example.Reactions.Controller;

import com.example.Reactions.feign.UserDTO;
import com.example.Reactions.model.ForWho;
import com.example.Reactions.model.Reaction;
import com.example.Reactions.model.ReactionType;
import com.example.Reactions.services.ReactionServices;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Reactions")
@AllArgsConstructor
@CrossOrigin("*")
public class ReactionController {

    private ReactionServices reactionService;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<Reaction> createReaction2(@RequestBody Reaction reaction) {
        reactionService.saveOrUpdateReaction(reaction);
        if (reaction != null) {
            return ResponseEntity.ok(reaction);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{userid}/{forWho}/{forWhoid}")
    public Reaction getReaction(@PathVariable("userid")Integer userid,@PathVariable("forWho")ForWho forWho,@PathVariable("forWhoid")Integer forwhoid)
    {
        try {
            return reactionService.getReaction(userid,forWho,forwhoid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @DeleteMapping("/delete//{userid}/{forWho}/{forWhoid}")
    public void delete(@PathVariable("userid")Integer userid,@PathVariable("forWho")ForWho forWho,@PathVariable("forWhoid")Integer forwhoid)
    {
        reactionService.delete(userid,forWho,forwhoid);
    }

}
