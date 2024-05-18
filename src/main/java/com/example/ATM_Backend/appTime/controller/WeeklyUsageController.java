package com.example.ATM_Backend.appTime.controller;

import com.example.ATM_Backend.appTime.model.AppTime;
import com.example.ATM_Backend.appTime.model.WeeklyUsage;
import com.example.ATM_Backend.appTime.service.AppTimeService;
import com.example.ATM_Backend.appTime.service.WeeklyUsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class WeeklyUsageController {

    private final WeeklyUsageService weeklyUsageService;

    @Autowired
    public WeeklyUsageController(WeeklyUsageService weeklyUsageService) {
        this.weeklyUsageService = weeklyUsageService;
    }

    @PostMapping("/weeklyUsage")
    public ResponseEntity<String> saveOrUpdateWeeklyUsage(@RequestBody WeeklyUsage weeklyUsage) {
        try {
            weeklyUsageService.saveOrUpdateWeeklyUsage(weeklyUsage);
            return ResponseEntity.ok("WeeklyUsage saved or updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save or update WeeklyUsage");
        }
    }


}
