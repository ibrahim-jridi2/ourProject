package com.example.Reactions.Controller;

import com.example.Reactions.feign.UserDTO;
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
    //create a reaction lel nimporte qui
    @PostMapping("/create")
    public ResponseEntity<Reaction> createReaction(@RequestBody Reaction reaction) {
        Reaction createdReaction = reactionService.create(reaction);
        return new ResponseEntity<>(createdReaction, HttpStatus.CREATED);
    }
    @GetMapping("/info/{blogid}")
    public ResponseEntity<Map<ReactionType, Long>> getReactionsCountForBlog(@PathVariable Integer blogid) {
        Map<ReactionType, Long> reactionCounts = reactionService.getReactionsCountByBlogId(blogid);
        return ResponseEntity.ok(reactionCounts);
    }
    //get all reaction
    @GetMapping("/ofblog/{idblog}")
    public List<Reaction> getreactionofblog(@PathVariable("idblog") Integer idblog) {
        return reactionService.getAllReactionsofBlog(idblog);
    }

    @GetMapping("/all_reactions")
    public List<Reaction> getAllReactions() {
        return reactionService.getAllReactions();
    }

    // ta3tini el 3bed eli 3amlou like 3ala el blog x
    @GetMapping("/{blogid}/{reactiontype}")
    public List<UserDTO> getAllReactionsbyblogandtype(@PathVariable("blogid")Integer idblog, @PathVariable("reactiontype") String reactiontype) {
        return reactionService.getReactionByblogandtype(idblog,reactiontype);
    }
    // ta3tini reaction by id
    @GetMapping("/{id}")
    public ResponseEntity<Reaction> getReactionById(@PathVariable Integer id) {
        Reaction reaction = reactionService.getReactionById(id);
        if (reaction != null) {
            return new ResponseEntity<>(reaction, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/blog_user/{iduser}/{blogid}")
    public ResponseEntity<ReactionType> getAllReactionsByBlogAndType(@PathVariable("blogid") Integer idblog,@PathVariable("iduser") Integer iduser) {
            ReactionType reactionType = reactionService.getMyReactionOfThisBlog(idblog, iduser);
            return ResponseEntity.ok(reactionType);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReaction(@PathVariable Integer id) {
        reactionService.deleteReaction(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/deleteByBlogAndUser/{id}/{idblog}")
    public void deleteReaction(@PathVariable("id")Integer id, @PathVariable("idblog") Integer idblog) {
         reactionService.deleteReactionbyboganduser(id ,idblog);
    }

}
