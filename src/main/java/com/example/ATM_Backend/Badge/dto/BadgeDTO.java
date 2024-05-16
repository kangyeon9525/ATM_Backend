        package com.example.ATM_Backend.Badge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter  // Lombok을 사용하여 getter 메서드 자동 생성
@Setter  // Lombok을 사용하여 setter 메서드 자동 생성
public class BadgeDTO {
    private Long id;  // 배지의 고유 ID
    private String name;  // 배지의 이름
    private String description;  // 배지의 설명
    private int criteria;  // 배지를 획득하기 위한 기준

    // 생성자: 모든 필드를 초기화
    public BadgeDTO(Long id, String name, String description, int criteria) {
        this.id = id;  // 배지 ID 설정
        this.name = name;  // 배지 이름 설정
        this.description = description;  // 배지 설명 설정
        this.criteria = criteria;  // 배지 기준 설정
    }
}