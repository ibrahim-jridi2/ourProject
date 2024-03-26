package com.campers.now.controllers;

import com.campers.now.models.CampingCenter;
import com.campers.now.services.CampingCenterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Camping Center Management")
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("camping-centers")
@CrossOrigin
public class CampingCenterController {
    private final CampingCenterService campingCenterService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void add(@RequestBody CampingCenter campingCenter) {
        campingCenterService.add(campingCenter);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

    @PostMapping("/addActivity")
    public CampingCenter addActivitybyCampingcenterId(@RequestParam("campingcenterId") Integer campingcenterId,  @RequestParam("activityId") Integer activityId) {
        return campingCenterService.addActivitybyCampingcenterId(campingcenterId, activityId);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("calculateOccupancyRate")
    public double[] calculateOccupancyRate(){
        return campingCenterService.calculateOccupancyRate();
    }
    @GetMapping("calculateADR")
    public  double[]  calculateADR(){
        return campingCenterService.calculateADR();
    }
    @GetMapping("calculateRevenuePerOccupiedSpace")
    public double calculateRevenuePerOccupiedSpace(){
        return campingCenterService.calculateRevenuePerOccupiedSpace();
    }

}
