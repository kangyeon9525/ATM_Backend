package com.example.ATM_Backend.appTime.controller;

import com.example.ATM_Backend.appTime.model.MonthlyUsage;
import com.example.ATM_Backend.appTime.service.MonthlyUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MonthlyUsageController {

    private final MonthlyUsageService monthlyUsageService;

    @Autowired
    public MonthlyUsageController(MonthlyUsageService monthlyUsageService) {
        this.monthlyUsageService = monthlyUsageService;
    }

    @PostMapping("/monthlyUsage")
    public ResponseEntity<String> saveOrUpdateMonthlyUsage(@RequestBody MonthlyUsage monthlyUsage) {
        try {
            monthlyUsageService.saveOrUpdateMonthlyUsage(monthlyUsage);
            return ResponseEntity.ok("MonthlyUsage updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save or update MonthlyUsage");
        }
    }


}
