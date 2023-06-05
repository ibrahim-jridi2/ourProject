package com.campers.now.services;

import com.campers.now.models.Activity;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ActivityService {

    List<Activity> getAll(Integer pageNumber, String property, Sort.Direction direction);

    Activity getById(Integer id);

    Activity add(Activity o);

    Activity update(Activity o);


}
