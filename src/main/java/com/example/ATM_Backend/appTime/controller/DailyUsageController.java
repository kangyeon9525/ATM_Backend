package com.example.ATM_Backend.appTime.controller;

import com.example.ATM_Backend.appTime.model.DailyUsage;
import com.example.ATM_Backend.appTime.service.DailyUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dailyusage")
public class DailyUsageController {

    private final DailyUsageService dailyUsageService;

    @Autowired
    public DailyUsageController(DailyUsageService dailyUsageService) {
        this.dailyUsageService = dailyUsageService;
    }

    @PostMapping("/post")
    public ResponseEntity<String> saveOrUpdateDailyUsage(@RequestBody DailyUsage dailyUsage) {
        try {
            dailyUsageService.saveOrUpdateDailyUsage(dailyUsage);
            return ResponseEntity.ok("DailyUsage updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save or update DailyUsage");
        }
    }

    @GetMapping("/get/{userName}")
    public ResponseEntity<List<DailyUsage>> getDailyUsageByUserName(@PathVariable("userName") String userName) {
        try {
            List<DailyUsage> dailyUsage = dailyUsageService.getDailyUsageByUserName(userName);
            if (dailyUsage.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(dailyUsage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/delete/{userName}")
    public ResponseEntity<String> deleteDailyUsageById(@PathVariable("userName") String userName) {
        try {
            boolean isDeleted = dailyUsageService.deleteDailyUsageByUserName(userName);
            if (isDeleted) {
                return ResponseEntity.ok("DailyUsage deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("DailyUsage not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete DailyUsage");
        }
    }


}

