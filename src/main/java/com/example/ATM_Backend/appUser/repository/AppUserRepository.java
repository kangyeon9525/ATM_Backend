package com.example.ATM_Backend.appUser.repository;

import com.example.ATM_Backend.appUser.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByusername(String username); //사용자 ID로 AppUser 엔티티를 조회하는 findByUsername 메서드
}
