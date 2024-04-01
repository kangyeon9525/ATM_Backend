package com.example.ATM_Backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

// 전체 사용량 화면
// 애플리케이션 사용량 데이터를 나타내는 JPA 엔티티 (데이터 모델)
// 앱 이름, 사용 시간, 사용 날짜 등 포함
@Entity
public class AppUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // AppUsage 엔티티를 위한 ID
    private String appName; // 애플리케이션 이름
    private Long duration; // 사용 시간(밀리초)
    private LocalDate date; // 데이터 수집 날짜
    //getter, setter 설정 필요
}
