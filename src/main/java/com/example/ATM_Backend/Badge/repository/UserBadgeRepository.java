package com.example.ATM_Backend.Badge.repository;

import com.example.ATM_Backend.Badge.model.UserBadge;
import com.example.ATM_Backend.Badge.model.Badge;
import com.example.ATM_Backend.appUser.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {
    List<UserBadge> findByUser(AppUser user);
    boolean existsByUserAndBadge(AppUser user, Badge badge);

    void deleteByUser(AppUser user);
}
