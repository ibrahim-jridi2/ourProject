package com.campers.now.services.Impl;

import com.campers.now.models.CampingCenter;
import com.campers.now.repositories.CampingCenterRepository;
import com.campers.now.services.CampingCenterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class CampingCenterServiceImpl implements CampingCenterService {
    CampingCenterRepository campingCenterRepository;
    private static final String UPLOAD_FOLDER = "C:\\Users\\khalil.jammezi\\OneDrive - Keyrus\\Bureau\\campers-spring\\src\\main\\java\\com\\campers\\now\\resources\\images";

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
        getById(o.getId());
        try {
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
    public void uploadImage(MultipartFile image) {
        try {
            image.transferTo(new File(UPLOAD_FOLDER + image.getOriginalFilename()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
