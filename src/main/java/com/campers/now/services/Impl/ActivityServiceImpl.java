package com.campers.now.services.Impl;

import com.campers.now.models.Activity;
import com.campers.now.repositories.ActivityRepository;
import com.campers.now.services.ActivityService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ActivityServiceImpl implements ActivityService {

    ActivityRepository activityRepository;

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
    @Transactional
    public Activity add(Activity o) {
        try {
            return activityRepository.save(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Activity update(Activity o) {
        getById(o.getId());
        try {
            return activityRepository.save(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
