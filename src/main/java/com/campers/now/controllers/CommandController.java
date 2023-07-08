package com.campers.now.controllers;

import com.campers.now.models.Command;
import com.campers.now.services.CommandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Command Management")
@RestController
@AllArgsConstructor
@RequestMapping("commands")
public class CommandController {
    private final CommandService commandService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_CAMPER')")
    public Command add(@RequestBody Command command) {
        return commandService.add(command);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CAMPER')")
    public Command update(@RequestBody Command command, @PathVariable("id") Integer id) {
        command.setId(id);
        return commandService.update(command);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CAMPER')")
    public Command getOne(@PathVariable("id") Integer id) {
        return commandService.getById(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_CAMPER')")
    public List<Command> getAll(@RequestParam(value = "page", required = false) Integer page,
                            @RequestParam(value = "sort", required = false) String sort,
                            @RequestParam(value = "dir", required = false) String dir) {

        Sort.Direction sortDir = Sort.Direction.fromString(StringUtils.hasText(dir) ? dir.toUpperCase() : Sort.Direction.ASC.name());
        return commandService.getAll(page, sort, sortDir);
    }
}
