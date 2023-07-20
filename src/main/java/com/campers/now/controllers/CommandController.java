package com.campers.now.controllers;

import com.campers.now.models.Command;
import com.campers.now.services.CommandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Command Management")
@RestController
@AllArgsConstructor
@RequestMapping("commands")
@CrossOrigin
public class CommandController {
    private final CommandService commandService;


    @GetMapping
    public ResponseEntity<List<Command>> getAllCommands() {
        List<Command> commands = commandService.getAllCommands();

            return ResponseEntity.ok(commands);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Command> getCommandById(@PathVariable("id") int id) {
        Command command = commandService.getCommandById(id);
        if (command != null) {
            return new ResponseEntity<>(command, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping

    public ResponseEntity<Command> createCommand(@RequestBody Command command) {
        Command createdCommand = commandService.createCommand(command);
        return new ResponseEntity<>(createdCommand, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")

    public ResponseEntity<Command> updateCommand(@PathVariable("id") int id, @RequestBody Command command) {
        Command updatedCommand = commandService.updateCommand(id, command);
        if (updatedCommand != null) {
            return new ResponseEntity<>(updatedCommand, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommand(@PathVariable("id") int id) {
        commandService.deleteCommand(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
