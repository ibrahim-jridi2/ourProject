package com.campers.now.controllers;

import com.campers.now.models.CampingCenter;
import com.campers.now.services.CampingCenterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Camping Center Management")
@RestController
@AllArgsConstructor
@RequestMapping("camping-centers")
public class CampingCenterController {
    private final CampingCenterService campingCenterService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CampingCenter add(@RequestBody CampingCenter campingCenter) {
        return campingCenterService.add(campingCenter);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CampingCenter update(@RequestBody CampingCenter campingCenter, @PathVariable("id") Integer id) {
        campingCenter.setId(id);
        return campingCenterService.update(campingCenter);
    }

    @GetMapping("/{id}")
    public CampingCenter getOne(@PathVariable("id") Integer id) {
        return campingCenterService.getById(id);
    }

    @GetMapping
    public List<CampingCenter> getAll(@RequestParam(value = "page", required = false) Integer page,
                            @RequestParam(value = "sort", required = false) String sort,
                            @RequestParam(value = "dir", required = false) String dir) {

        Sort.Direction sortDir = Sort.Direction.fromString(StringUtils.hasText(dir) ? dir.toUpperCase() : Sort.Direction.ASC.name());
        return campingCenterService.getAll(page, sort, sortDir);
    }
}
