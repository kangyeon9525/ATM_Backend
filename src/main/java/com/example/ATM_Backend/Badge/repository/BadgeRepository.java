package com.example.ATM_Backend.Badge.repository;

import com.example.ATM_Backend.Badge.model.Badge;  // Badge 엔티티를 import
import org.springframework.data.jpa.repository.JpaRepository;  // JpaRepository를 import

// Badge 엔티티에 대한 JPA 리포지토리 인터페이스를 정의
public interface BadgeRepository extends JpaRepository<Badge, Long> {}
