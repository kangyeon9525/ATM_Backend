package com.example.ATM_Backend.controller;

import com.example.ATM_Backend.model.AppUsage;
import com.example.ATM_Backend.service.AppUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

// 전체 사용량 화면
// AppUsage 데이터에 대한 HTTP요청을 처리하는 컨트롤러
// 전체 사용량, 상위 어플리케이션 사용량 조회하는 API 포함
@RestController
@RequestMapping("/appusage") // API의 기본 경로 설정
public class AppUsageController {
    private final AppUsageService appUsageService;

    // AppUsageService 자동 주입하기 위한 생성자
    @Autowired
    public AppUsageController(AppUsageService appUsageService) {
        this.appUsageService = appUsageService;
    }

    // 특정 기간 동안의 앱 사용량 데이터를 가져오는 HTTP GET 요청 처리
    @GetMapping
    public ResponseEntity<List<AppUsage>> getAppUsage(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate end) {
        List<AppUsage> appUsageList = appUsageService.getAppUsageBetweenDates(start, end);
        return ResponseEntity.ok(appUsageList);
    }
}
