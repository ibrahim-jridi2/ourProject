package com.campers.now.controllers;

import com.campers.now.models.Activity;
import com.campers.now.models.CampingCenter;

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
import java.util.Map;

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

    @PutMapping("/{id}/{campId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Activity update(@RequestBody Activity activity, @PathVariable("id") Integer id,@PathVariable("campId") Integer campId) {
        activity.setId(id);
        return activityService.update(activity,campId);
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

    @GetMapping("/active")
    public List<Activity> getActiveActivities(@RequestParam(value = "page", required = false) Integer page,
                                              @RequestParam(value = "sort", required = false) String sort,
                                              @RequestParam(value = "dir", required = false) String dir) {

        Sort.Direction sortDir = Sort.Direction.fromString(StringUtils.hasText(dir) ? dir.toUpperCase() : Sort.Direction.ASC.name());
        return activityService.getActiveActivities(page, sort, sortDir);
    }


   @PostMapping("addTofavorites/{activityId}/{userId}")
    public ResponseEntity<?> addActivityToUser(@PathVariable Integer activityId, @PathVariable Integer userId) {
        return activityService.addFavorite(activityId, userId);
    }

  @GetMapping("/favorites/{userId}")
    public List<Activity> getFavoritesActivities(@RequestParam(value = "page", required = false) Integer page,
                                                 @RequestParam(value = "sort", required = false) String sort,
                                                 @RequestParam(value = "dir", required = false) String dir,
                                                 @PathVariable Integer userId) {

        Sort.Direction sortDir = Sort.Direction.fromString(StringUtils.hasText(dir) ? dir.toUpperCase() : Sort.Direction.ASC.name());
        return activityService.getFavoritesActivities(page, sort, sortDir,userId);
    }

    @GetMapping("/Notfavorites/{userId}")
    public List<Activity> getNotFavoritesActivities(@RequestParam(value = "page", required = false) Integer page,
                                                 @RequestParam(value = "sort", required = false) String sort,
                                                 @RequestParam(value = "dir", required = false) String dir,
                                                 @PathVariable Integer userId) {

        Sort.Direction sortDir = Sort.Direction.fromString(StringUtils.hasText(dir) ? dir.toUpperCase() : Sort.Direction.ASC.name());
        return activityService.getNotFavoritesActivities(page, sort, sortDir,userId);
    }

    @GetMapping("/AllActivities/{userId}")
    public List<Activity> getActivitiesListForUser(@RequestParam(value = "page", required = false) Integer page,
                                                    @RequestParam(value = "sort", required = false) String sort,
                                                    @RequestParam(value = "dir", required = false) String dir,
                                                    @PathVariable Integer userId) {

        Sort.Direction sortDir = Sort.Direction.fromString(StringUtils.hasText(dir) ? dir.toUpperCase() : Sort.Direction.ASC.name());
        return activityService.getActivitiesListForUser(page, sort, sortDir,userId);
    }

    @PostMapping("deleteFromfavorites/{activityId}/{userId}")
    public ResponseEntity<?> deleteFromFavorites(@PathVariable Integer activityId, @PathVariable Integer userId) {
        return activityService.deleteFromFavorite(activityId, userId);
    }


    @GetMapping("/campsByActivity/{actId}")
    public List<CampingCenter> getCampingCentersByActId(@RequestParam(value = "page", required = false) Integer page,
                                                        @RequestParam(value = "sort", required = false) String sort,
                                                        @RequestParam(value = "dir", required = false) String dir,
                                                        @PathVariable Integer actId) {

        Sort.Direction sortDir = Sort.Direction.fromString(StringUtils.hasText(dir) ? dir.toUpperCase() : Sort.Direction.ASC.name());
        return activityService.getCampingsList(page, sort, sortDir,actId);
    }

    @GetMapping("/top5-most-reserved")
    public ResponseEntity<List<Object>> getTop5MostReservedActivities() {
        // Call the service method to get the top 5 most reserved activities
        List<Object> top5Activities = activityService.getTop5MostReservedActivities();

        // Return the results as a response entity (You can also return them as JSON or any other format)
        return ResponseEntity.ok(top5Activities);
    }

}
