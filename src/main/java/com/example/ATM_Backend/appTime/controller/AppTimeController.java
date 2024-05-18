package com.example.ATM_Backend.appTime.controller;

import com.example.ATM_Backend.appTime.model.AppTime;
import com.example.ATM_Backend.appTime.service.AppTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apptime")
public class AppTimeController {

    private final AppTimeService appTimeService;

    @Autowired
    public AppTimeController(AppTimeService appTimeService) {
        this.appTimeService = appTimeService;
    }

    @PostMapping("/post")
    public ResponseEntity<String> saveOrUpdateAppTime(@RequestBody AppTime appTime) {
        try {
            appTimeService.saveOrUpdateAppTime(appTime);
            return ResponseEntity.ok("AppTime saved or updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save or update AppTime");
        }
    }

    @GetMapping("/get/{userName}")
    public ResponseEntity<List<AppTime>> getAppTimeByUserName(@PathVariable("userName") String userName) {
        try {
            List<AppTime> appTimes = appTimeService.getAppTimeByUserName(userName);
            if (appTimes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(appTimes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/delete/{userName}")
    public ResponseEntity<String> deleteAppTimeById(@PathVariable("userName") String userName) {
        try {
            boolean isDeleted = appTimeService.deleteAppTimeByUserName(userName);
            if (isDeleted) {
                return ResponseEntity.ok("AppTime deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("AppTime not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete AppTime");
        }
    }

}
