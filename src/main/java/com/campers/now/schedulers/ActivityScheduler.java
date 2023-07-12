package com.campers.now.schedulers;

import com.campers.now.services.ActivityService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ActivityScheduler {
    private final ActivityService activityService;

    public ActivityScheduler(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Scheduled(cron = "0 0 0 * * *") // Runs daily at midnight
    public void updateActivityStatus() {
        activityService.updateActivityStatus();
    }
}