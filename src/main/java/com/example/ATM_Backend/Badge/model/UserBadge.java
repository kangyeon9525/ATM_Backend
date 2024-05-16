package com.example.ATM_Backend.Badge.model;

import com.example.ATM_Backend.appUser.model.AppUser;  // AppUser 클래스를 import
import jakarta.persistence.*;  // JPA 관련 어노테이션들을 import
import lombok.Getter;  // Lombok의 getter 메서드 생성 어노테이션 import
import lombok.Setter;  // Lombok의 setter 메서드 생성 어노테이션 import

@Getter  // Lombok을 사용하여 getter 메서드 자동 생성
@Setter  // Lombok을 사용하여 setter 메서드 자동 생성
@Entity  // 이 클래스가 JPA 엔티티임을 명시
public class UserBadge {
    @Id  // 기본 키(primary key)로 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ID 값 자동 생성 전략 설정
    private Long id;  // 사용자 배지의 고유 ID

    @ManyToOne  // 다대일 관계 설정: 여러 UserBadge가 하나의 AppUser에 연결
    @JoinColumn(name = "user_id")  // 외래 키(Foreign Key)로 사용할 컬럼 명 설정
    private AppUser user;  // 배지를 소유한 사용자

    @ManyToOne  // 다대일 관계 설정: 여러 UserBadge가 하나의 Badge에 연결
    @JoinColumn(name = "badge_id")  // 외래 키(Foreign Key)로 사용할 컬럼 명 설정
    private Badge badge;  // 사용자에게 부여된 배지

    private boolean achieved;  // 배지를 획득했는지 여부
}
