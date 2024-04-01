package com.example.ATM_Backend.service;

import com.example.ATM_Backend.model.AppUsage;
import com.example.ATM_Backend.repository.AppUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

// 전체 사용량 화면
// 앱 사용 데이터 관리하는 서비스. 사용량 데이터 조회, 분석하는 로직 담당
// AppUsage 데이터 처리 로직을 구현 (비즈니스 로직을 처리하는 서비스 레이어)
public class AppUsageService {
    private final AppUsageRepository appUsageRepository;

    // AppUsageRepository를 자동으로 주입하기 위한 생성자
    @Autowired
    public AppUsageService(AppUsageRepository appUsageRepository) {
        this.appUsageRepository = appUsageRepository;
    }
    // 특정 기간 동안의 AppUsage 데이터를 가져오는 메소드
    public List<AppUsage> getAppUsageBetweenDates(LocalDate start, LocalDate end) {
        return appUsageRepository.findByDateBetween(start, end);
    }

}
