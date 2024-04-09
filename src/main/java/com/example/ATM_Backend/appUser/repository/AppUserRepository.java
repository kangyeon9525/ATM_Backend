package com.example.ATM_Backend.appUser.repository;

import com.example.ATM_Backend.appUser.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
}
