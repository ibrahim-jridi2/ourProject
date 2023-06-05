package com.campers.now.services;

import com.campers.now.models.CampingCenter;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CampingCenterService {
    List<CampingCenter> getAll(Integer pageNumber, String property, Sort.Direction direction);

    CampingCenter getById(Integer id);

    CampingCenter add(CampingCenter o);

    CampingCenter update(CampingCenter o);
}
