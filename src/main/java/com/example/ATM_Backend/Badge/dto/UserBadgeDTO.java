package com.example.ATM_Backend.Badge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter  // Lombok을 사용하여 getter 메서드 자동 생성
@Setter  // Lombok을 사용하여 setter 메서드 자동 생성
public class UserBadgeDTO {
    private Long id;  // 사용자 배지의 고유 ID
    private BadgeDTO badge;  // 배지 정보 (BadgeDTO 타입)
    private boolean achieved;  // 배지를 획득했는지 여부

    // 생성자: 모든 필드를 초기화
    public UserBadgeDTO(Long id, BadgeDTO badge, boolean achieved) {
        this.id = id;  // 사용자 배지 ID 설정
        this.badge = badge;  // 배지 정보 설정
        this.achieved = achieved;  // 배지 획득 여부 설정
    }
}