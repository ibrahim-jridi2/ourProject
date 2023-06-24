package com.campers.now.controllers;

import com.campers.now.models.CampingCenter;
import com.campers.now.services.CampingCenterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Camping Center Management")
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("camping-centers")
@CrossOrigin(origins = "http://localhost:4200")
public class CampingCenterController {
    private final CampingCenterService campingCenterService;

    @PostMapping
    public void add(@RequestBody CampingCenter campingCenter) {
        campingCenterService.add(campingCenter);
    }

    @PutMapping("/{id}")
    public CampingCenter update(@RequestBody CampingCenter campingCenter, @PathVariable("id") Integer id) {
        campingCenter.setId(id);
        return campingCenterService.update(campingCenter);
    }

    @GetMapping("/{id}")
    public CampingCenter getOne(@PathVariable("id") Integer id) {
        return campingCenterService.getById(id);
    }

    @GetMapping
    public List<CampingCenter> getAll() {

        return campingCenterService.getAll();
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        campingCenterService.delete(id);
    }
  @PostMapping("/upload")
    public void uploadImage(@RequestBody MultipartFile image) {
        campingCenterService.uploadImage(image);
    }
}
