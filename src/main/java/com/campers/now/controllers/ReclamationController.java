package com.campers.now.controllers;

import com.campers.now.models.Reclamation;
import com.campers.now.services.ReclamationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Reclamation Management")
@RestController
@AllArgsConstructor
@RequestMapping("reclamations")
@CrossOrigin(origins = "http://localhost:4200")
public class ReclamationController {
    private final ReclamationService reclamationService;

    @PostMapping
    public Reclamation add(@RequestBody Reclamation reclamation) {
        return reclamationService.add(reclamation);
    }

    @PutMapping("/{id}")
    public Reclamation update(@RequestBody Reclamation reclamation, @PathVariable("id") Integer id) {
        reclamation.setId(id);
        return reclamationService.update(reclamation);
    }

    @GetMapping("/{id}")
    @Secured("ROLE_SUPER_ADMIN")
    public Reclamation getOne(@PathVariable("id") Integer id) {
        return reclamationService.getById(id);
    }

    @GetMapping
    @Secured("ROLE_SUPER_ADMIN")
    public List<Reclamation> getAll(@RequestParam(value = "page", required = false) Integer page,
                            @RequestParam(value = "sort", required = false) String sort,
                            @RequestParam(value = "dir", required = false) String dir) {

        Sort.Direction sortDir = Sort.Direction.fromString(StringUtils.hasText(dir) ? dir.toUpperCase() : Sort.Direction.ASC.name());
        return reclamationService.getAll(page, sort, sortDir);
    }
}
