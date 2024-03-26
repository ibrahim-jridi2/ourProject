package com.campers.now.services;

import com.campers.now.models.CampingCenter;

import java.util.List;

public interface CampingCenterService {
    List<CampingCenter> getAll();

    CampingCenter getById(Integer id);

    CampingCenter add(CampingCenter o);

    CampingCenter update(CampingCenter o);

    void delete(Integer id);

    CampingCenter addActivitybyCampingcenterId(Integer campingcenterId, Integer activityId);
    double[] calculateOccupancyRate() ;
    double[]  calculateADR();
    double calculateRevenuePerOccupiedSpace();




    }
