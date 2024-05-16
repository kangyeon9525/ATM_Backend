package com.example.ATM_Backend.Badge.model;

import jakarta.persistence.*;  // JPA 관련 어노테이션들을 import
import lombok.Getter;  // Lombok의 getter 메서드 생성 어노테이션 import
import lombok.Setter;  // Lombok의 setter 메서드 생성 어노테이션 import

@Getter  // Lombok을 사용하여 getter 메서드 자동 생성
@Setter  // Lombok을 사용하여 setter 메서드 자동 생성
@Entity  // 이 클래스가 JPA 엔티티임을 명시
public class Badge {

    @Id  // 기본 키(primary key)로 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ID 값 자동 생성 전략 설정
    private Long id;  // 배지의 고유 ID

    private String name;  // 배지의 이름
    private String description;  // 배지의 설명
    private int criteria;  // 배지를 획득하기 위한 기준
}
