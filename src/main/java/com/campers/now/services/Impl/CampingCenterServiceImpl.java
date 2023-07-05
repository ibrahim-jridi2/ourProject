package com.campers.now.services.Impl;

import com.campers.now.models.CampingCenter;
import com.campers.now.repositories.ActivityRepository;
import com.campers.now.repositories.CampingCenterRepository;
import com.campers.now.services.CampingCenterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class CampingCenterServiceImpl implements CampingCenterService {
    CampingCenterRepository campingCenterRepository;
    ActivityRepository activityRepository;

    @Override
    public List<CampingCenter> getAll() {

            return campingCenterRepository.findAll();

    }

    @Override
    public CampingCenter getById(Integer id) {
        return campingCenterRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public CampingCenter add(CampingCenter o) {
        try {
            CampingCenter campingCenter = campingCenterRepository.save(o);
            if (campingCenter.getActivities() != null && !campingCenter.getActivities().isEmpty())
                campingCenter.getActivities().forEach(activity -> activity.setCampingCenter(campingCenter));
            return campingCenter;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public CampingCenter update(CampingCenter o) {
        try {
            getById(o.getId());
            return campingCenterRepository.save(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        getById(id);
        try {
            campingCenterRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CampingCenter addActivitybyCampingcenterId(Integer campingcenterId, Integer activityId) {
        CampingCenter campingCenter = campingCenterRepository.findById(campingcenterId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        campingCenter.getActivities().add(activityRepository.findById(activityId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND)));
        return campingCenterRepository.save(campingCenter);
    }


}
