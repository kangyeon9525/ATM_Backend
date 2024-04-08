package com.example.ATM_Backend.user.repository;

import com.example.ATM_Backend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
