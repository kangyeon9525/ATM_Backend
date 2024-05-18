package com.example.ATM_Backend.appTime.controller;

import com.example.ATM_Backend.appTime.model.DailyUsage;
import com.example.ATM_Backend.appTime.service.DailyUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DailyUsageController {

    private final DailyUsageService dailyUsageService;

    @Autowired
    public DailyUsageController(DailyUsageService dailyUsageService) {
        this.dailyUsageService = dailyUsageService;
    }

    @PostMapping("/dailyUsage")
    public ResponseEntity<String> saveOrUpdateDailyUsage(@RequestBody DailyUsage dailyUsage) {
        try {
            dailyUsageService.saveOrUpdateDailyUsage(dailyUsage);
            return ResponseEntity.ok("DailyUsage updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save or update DailyUsage");
        }
    }


}

