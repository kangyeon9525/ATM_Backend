package com.example.ATM_Backend.Badge.repository;

import com.example.ATM_Backend.Badge.model.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {}
