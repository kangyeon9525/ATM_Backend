package com.example.ATM_Backend.repository;

import com.example.ATM_Backend.model.AppUsage;
import com.example.ATM_Backend.service.AppUsageService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
// 전체 사용량 화면
// JpaRepository 상속 => 앱 사용량 데이터에 대한 DB 접근 추상화
// AppUsage 데이터에 접근하는 리포지토리 생성
// AppUsage 엔티티를 위한 Spring Data JPA 리포지토리 인터페이스
@Service
public interface AppUsageRepository extends JpaRepository<AppUsage, Long> {
    // 특정 기간 동안의 AppUsage 데이터를 찾기 위한 메소드
    List<AppUsage> findByDateBetween(LocalDate start, LocalDate end);

}
