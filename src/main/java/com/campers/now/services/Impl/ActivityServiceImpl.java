package com.campers.now.services.Impl;

import com.campers.now.models.Activity;
import com.campers.now.models.CampingCenter;
import com.campers.now.models.User;
import com.campers.now.repositories.ActivityRepository;
import com.campers.now.repositories.CampingCenterRepository;
import com.campers.now.repositories.UserRepository;
import com.campers.now.services.ActivityService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ActivityServiceImpl implements ActivityService {

    ActivityRepository activityRepository;
    CampingCenterRepository campingCenterRepository;
    UserRepository userRepository;

    @Override
    public List<Activity> getAll(Integer pageNumber, String property, Sort.Direction direction) {
        if (pageNumber == null)
            return activityRepository.findAll();
        return activityRepository.findAll(PageRequest.of((pageNumber <= 0 ? 1 : pageNumber) - 1, 10, Sort.by(List.of(Sort.Order.by(StringUtils.hasText(property) ? property : "id").with(direction)))))
                .stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Activity getById(Integer id) {
        return activityRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Override

    public Activity addActivitybyCampingcenterId(Integer campingcenterId, Activity activity) {
        CampingCenter campingCenter = campingCenterRepository.findById(campingcenterId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        activity.setCampingCenter(campingCenter);
        return activityRepository.save(activity);
    }


    @Override
       public Activity update(Activity o, Integer campingcenterId) {
        String currentSeason = getCurrentSeason();
        CampingCenter campingCenter = campingCenterRepository.findById(campingcenterId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        o.setCampingCenter(campingCenter);
        getById(o.getId());

        try {
            if (o.getSeason().toString() == currentSeason) {
                o.setActive(true);
            } else {
                o.setActive(false);
            }
            return activityRepository.save(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void updateActivityStatus() {
        List<Activity> activities = activityRepository.findAll();
        String currentSeason = getCurrentSeason();

        for (Activity activity : activities) {
            if (activity.getSeason().toString() == currentSeason) {
                activity.setActive(true);
            } else {
                activity.setActive(false);
            }

            activityRepository.save(activity);
        }
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

    public List<Activity> getActiveActivities(Integer pageNumber, String property, Sort.Direction direction) {
        if (pageNumber == null) {
            return activityRepository.findByActiveTrue();
        } else {
            Sort.Order order = StringUtils.hasText(property)
                    ? Sort.Order.by(property).with(direction).ignoreCase()
                    : Sort.Order.by("id").with(direction);

            PageRequest pageRequest = PageRequest.of((pageNumber <= 0 ? 1 : pageNumber) - 1, 10, Sort.by(order));

            return activityRepository.findByActiveTrue(pageRequest).stream()
                    .collect(Collectors.toUnmodifiableList());
        }
    }

    public ResponseEntity<?> addFavorite(Integer activityId, Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Activity> optionalActivity = activityRepository.findById(activityId);

        if (optionalUser.isPresent() && optionalActivity.isPresent()) {
            User user = optionalUser.get();
            Activity activity = optionalActivity.get();

            user.getActivities().add(activity);
            userRepository.save(user);
            activity.setFavorite(true);
            activityRepository.save(activity);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public List<Activity> getFavoritesActivities(Integer pageNumber, String property, Sort.Direction direction, Integer userId) {
        return activityRepository.findFavoritesActivitiesByUserId(userId);
    }

    public List<Activity> getNotFavoritesActivities(Integer pageNumber, String property, Sort.Direction direction, Integer userId) {
        return activityRepository.findNotFavoritesActivitiesForUserId(userId);
    }

    @Override
    public List<Activity> getActivitiesListForUser(Integer pageNumber, String property, Sort.Direction direction, Integer userId) {
        List<Activity> activityList = new ArrayList<>();
        getFavoritesActivities(pageNumber, property, direction, userId).stream().forEach(activity -> activity.setFavorite(true));
        getNotFavoritesActivities(pageNumber, property, direction, userId).stream().forEach(activity -> activity.setFavorite(false));

        for (int i=0;i<getNotFavoritesActivities(pageNumber, property, direction, userId).size();i++){
            if (getFavoritesActivities(pageNumber, property, direction, userId).contains(getNotFavoritesActivities(pageNumber, property, direction, userId).get(i).getId())){
                getNotFavoritesActivities(pageNumber, property, direction, userId).remove(getNotFavoritesActivities(pageNumber, property, direction, userId).get(i));
            }
        }
        activityList.addAll(getFavoritesActivities(pageNumber, property, direction, userId));
        activityList.addAll(getNotFavoritesActivities(pageNumber, property, direction, userId));
        return activityList;
    }

    public ResponseEntity<?> deleteFromFavorite(Integer activityId, Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Activity> optionalActivity = activityRepository.findById(activityId);

        if (optionalUser.isPresent() && optionalActivity.isPresent()) {
            User user = optionalUser.get();
            Activity activity = optionalActivity.get();

            user.getActivities().remove(activity);
            userRepository.save(user);
            activity.setFavorite(false);
            activityRepository.save(activity);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    public List<CampingCenter> getCampingsList(Integer pageNumber, String property, Sort.Direction direction, Integer actId) {
        return activityRepository.findCampingCentersByActId(actId);
    }



    public List<Object> getTop5MostReservedActivities() {
        // Call the custom query method to get the top 5 most reserved activities
        return activityRepository.findTop();
    }
}

