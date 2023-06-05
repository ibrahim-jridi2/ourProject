package com.campers.now.services.Impl;

import com.campers.now.models.CampingCenter;
import com.campers.now.repositories.CampingCenterRepository;
import com.campers.now.services.CampingCenterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class CampingCenterServiceImpl implements CampingCenterService {
    CampingCenterRepository campingCenterRepository;

    @Override
    public List<CampingCenter> getAll(Integer pageNumber, String property, Sort.Direction direction) {
        if (pageNumber == null)
            return campingCenterRepository.findAll();
        return campingCenterRepository.findAll(PageRequest.of((pageNumber <= 0 ? 1 : pageNumber) - 1, 10, Sort.by(List.of(Sort.Order.by(StringUtils.hasText(property) ? property : "id").with(direction)))))
                .stream().collect(Collectors.toUnmodifiableList());
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
        getById(o.getId());
        try {
            return campingCenterRepository.save(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
