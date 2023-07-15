package com.campers.now.services;

import com.campers.now.models.Activity;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ActivityService {

    List<Activity> getAll(Integer pageNumber, String property, Sort.Direction direction);


    Activity getById(Integer id);

    Activity addActivitybyCampingcenterId(Integer campingcenterId, Activity activity);

    Activity update(Activity o);

    public void updateActivityStatus();

    public List<Activity> getActiveActivities(Integer pageNumber, String property, Sort.Direction direction);

    public ResponseEntity<?> addFavorite(Integer userId, Integer activityId);

}
