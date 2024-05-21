package com.example.ATM_Backend.Badge.repository;

import com.example.ATM_Backend.Badge.model.UserBadge;  // UserBadge 엔티티를 import
import com.example.ATM_Backend.Badge.model.Badge;  // Badge 엔티티를 import
import com.example.ATM_Backend.appUser.model.AppUser;  // AppUser 엔티티를 import
import org.springframework.data.jpa.repository.JpaRepository;  // JpaRepository를 import

import java.util.List;  // List를 import

// UserBadge 엔티티에 대한 JPA 리포지토리 인터페이스를 정의
public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {
    // 특정 사용자(AppUser)에 대한 UserBadge 목록을 조회하는 메서드
    List<UserBadge> findByUser(AppUser user);

    // user와 badge를 기준으로 UserBadge 존재 여부를 확인하는 메서드 추가
    boolean existsByUserAndBadge(AppUser user, Badge badge);
}
