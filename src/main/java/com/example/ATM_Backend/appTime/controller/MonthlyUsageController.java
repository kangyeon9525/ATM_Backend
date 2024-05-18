package com.example.ATM_Backend.appTime.controller;

import com.example.ATM_Backend.appTime.model.MonthlyUsage;
import com.example.ATM_Backend.appTime.service.MonthlyUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monthlyusage")
public class MonthlyUsageController {

    private final MonthlyUsageService monthlyUsageService;

    @Autowired
    public MonthlyUsageController(MonthlyUsageService monthlyUsageService) {
        this.monthlyUsageService = monthlyUsageService;
    }

    @PostMapping("/post")
    public ResponseEntity<String> saveOrUpdateMonthlyUsage(@RequestBody MonthlyUsage monthlyUsage) {
        try {
            monthlyUsageService.saveOrUpdateMonthlyUsage(monthlyUsage);
            return ResponseEntity.ok("MonthlyUsage updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save or update MonthlyUsage");
        }
    }

    @GetMapping("/get/{userName}")
    public ResponseEntity<List<MonthlyUsage>> getMonthlyUsageByUserName(@PathVariable("userName") String userName) {
        try {
            List<MonthlyUsage> monthlyUsage = monthlyUsageService.getMonthlyUsageByUserName(userName);
            if (monthlyUsage.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(monthlyUsage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/delete/{userName}")
    public ResponseEntity<String> deleteMonthlyUsageById(@PathVariable("userName") String userName) {
        try {
            boolean isDeleted = monthlyUsageService.deleteMonthlyUsageByUserName(userName);
            if (isDeleted) {
                return ResponseEntity.ok("MonthlyUsage deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("MonthlyUsage not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete MonthlyUsage");
        }
    }

}
