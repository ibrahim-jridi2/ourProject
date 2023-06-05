package com.campers.now.controllers;

import com.campers.now.models.Reclamation;
import com.campers.now.services.ReclamationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Reclamation Management")
@RestController
@AllArgsConstructor
@RequestMapping("reclamations")
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
    public Reclamation getOne(@PathVariable("id") Integer id) {
        return reclamationService.getById(id);
    }

    @GetMapping
    public List<Reclamation> getAll(Integer page, String sort, String dir) {

        Sort.Direction sortDir = Sort.Direction.fromString(StringUtils.hasText(dir) ? dir.toUpperCase() : Sort.Direction.ASC.name());
        return reclamationService.getAll(page, sort, sortDir);
    }
}
