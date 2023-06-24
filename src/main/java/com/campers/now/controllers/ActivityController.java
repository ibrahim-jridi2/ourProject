package com.campers.now.controllers;

import com.campers.now.models.Activity;
import com.campers.now.services.ActivityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Activity Management")
@RestController
@AllArgsConstructor
@RequestMapping("activities")
@CrossOrigin(origins = "http://localhost:4200")

public class ActivityController {
    ActivityService activityService;

    @PostMapping
    public Activity add(@RequestBody Activity activity) {
        return activityService.add(activity);
    }

    @PutMapping("/{id}")
    public Activity update(@RequestBody Activity activity, @PathVariable("id") Integer id) {
        activity.setId(id);
        return activityService.update(activity);
    }

    @GetMapping("/{id}")
    public Activity getOne(@PathVariable("id") Integer id) {
        return activityService.getById(id);
    }

    @GetMapping
    public List<Activity> getAll(@RequestParam(value = "page", required = false) Integer page,
                            @RequestParam(value = "sort", required = false) String sort,
                            @RequestParam(value = "dir", required = false) String dir) {

        Sort.Direction sortDir = Sort.Direction.fromString(StringUtils.hasText(dir) ? dir.toUpperCase() : Sort.Direction.ASC.name());
        return activityService.getAll(page, sort, sortDir);
    }
}
