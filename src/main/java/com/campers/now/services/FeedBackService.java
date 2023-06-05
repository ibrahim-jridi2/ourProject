package com.campers.now.services;

import com.campers.now.models.FeedBack;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface FeedBackService {
    List<FeedBack> getAll(Integer pageNumber, String property, Sort.Direction direction);

    FeedBack getById(Integer id);

    FeedBack add(FeedBack o);

    FeedBack update(FeedBack o);
}
