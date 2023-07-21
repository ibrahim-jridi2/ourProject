package com.campers.now.services;

import com.campers.now.models.Activity;
import com.campers.now.models.CampingCenter;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ActivityService {

    List<Activity> getAll(Integer pageNumber, String property, Sort.Direction direction);


    Activity getById(Integer id);

    Activity addActivitybyCampingcenterId(Integer campingcenterId, Activity activity);

    Activity update(Activity o,Integer campingcenterId);

    public void updateActivityStatus();

    public List<Activity> getActiveActivities(Integer pageNumber, String property, Sort.Direction direction);

    public ResponseEntity<?> addFavorite(Integer activityId, Integer userId);

    public List<Activity> getFavoritesActivities(Integer pageNumber, String property, Sort.Direction direction, Integer userId);
    public List<Activity> getNotFavoritesActivities(Integer pageNumber, String property, Sort.Direction direction, Integer userId);

    public List<Activity> getActivitiesListForUser(Integer pageNumber, String property, Sort.Direction direction, Integer userId);

    public ResponseEntity<?> deleteFromFavorite(Integer activityId, Integer userId);

    public List<CampingCenter> getCampingsList(Integer pageNumber, String property, Sort.Direction direction, Integer actId);

    public List<Object> getTop5MostReservedActivities() ;

    }
