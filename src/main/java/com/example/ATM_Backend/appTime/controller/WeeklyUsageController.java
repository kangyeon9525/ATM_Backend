package com.example.ATM_Backend.appTime.controller;

import com.example.ATM_Backend.appTime.model.WeeklyUsage;
import com.example.ATM_Backend.appTime.service.WeeklyUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weeklyusage")
public class WeeklyUsageController {

    private final WeeklyUsageService weeklyUsageService;

    @Autowired
    public WeeklyUsageController(WeeklyUsageService weeklyUsageService) {
        this.weeklyUsageService = weeklyUsageService;
    }

    @PostMapping("/post")
    public ResponseEntity<String> saveOrUpdateWeeklyUsage(@RequestBody WeeklyUsage weeklyUsage) {
        try {
            weeklyUsageService.saveOrUpdateWeeklyUsage(weeklyUsage);
            return ResponseEntity.ok("WeeklyUsage saved or updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save or update WeeklyUsage");
        }
    }

    @GetMapping("/get/{userName}")
    public ResponseEntity<List<WeeklyUsage>> getWeeklyUsageByUserName(@PathVariable("userName") String userName) {
        try {
            List<WeeklyUsage> weeklyUsage = weeklyUsageService.getWeeklyUsageByUserName(userName);
            if (weeklyUsage.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(weeklyUsage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/delete/{userName}")
    public ResponseEntity<String> deleteWeeklyUsageById(@PathVariable("userName") String userName) {
        try {
            boolean isDeleted = weeklyUsageService.deleteWeeklyUsageByUserName(userName);
            if (isDeleted) {
                return ResponseEntity.ok("WeeklyUsage deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("WeeklyUsage not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete WeeklyUsage");
        }
    }


}
