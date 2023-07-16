package com.campers.now.controllers;

import com.campers.now.models.Activity;
import com.campers.now.services.ActivityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Activity Management")
@RestController
@AllArgsConstructor
@RequestMapping("activities")
@CrossOrigin(origins = "http://localhost:4200")
public class ActivityController {
    ActivityService activityService;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Activity addActivitybyCampingcenterId(@RequestParam("campingcenterId") Integer campingcenterId, @RequestBody Activity activity) {
        String currentSeason = getCurrentSeason();
        if (activity.getSeason().toString() == currentSeason) {
            activity.setActive(true);
        } else {
            activity.setActive(false);
        }
        return activityService.addActivitybyCampingcenterId(campingcenterId, activity);
    }

    private String getCurrentSeason() {
        LocalDate currentDate = LocalDate.now();
        int month = currentDate.getMonthValue();

        if (month >= 3 && month <= 5) {
            return "SPRING";
        } else if (month >= 6 && month <= 8) {
            return "SUMMER";
        } else if (month >= 9 && month <= 11) {
            return "AUTUMN";
        } else {
            return "WINTER";
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

    @GetMapping("/upcoming")
    public List<Activity> getActiveActivities(@RequestParam(value = "page", required = false) Integer page,
                                              @RequestParam(value = "sort", required = false) String sort,
                                              @RequestParam(value = "dir", required = false) String dir) {

        Sort.Direction sortDir = Sort.Direction.fromString(StringUtils.hasText(dir) ? dir.toUpperCase() : Sort.Direction.ASC.name());
        return activityService.getActiveActivities(page, sort, sortDir);
    }


   @PostMapping("favorites/{activityId}/{userId}")
    public ResponseEntity<?> addActivityToUser(@PathVariable Integer activityId, @PathVariable Integer userId) {
        return activityService.addFavorite(userId, activityId);
    }

  @GetMapping("/favorites/{userId}")
    public List<Activity> getFavoritesActivities(@RequestParam(value = "page", required = false) Integer page,
                                                 @RequestParam(value = "sort", required = false) String sort,
                                                 @RequestParam(value = "dir", required = false) String dir,
                                                 @PathVariable Integer userId) {

        Sort.Direction sortDir = Sort.Direction.fromString(StringUtils.hasText(dir) ? dir.toUpperCase() : Sort.Direction.ASC.name());
        return activityService.getFavoritesActivities(page, sort, sortDir,userId);
    }

}
