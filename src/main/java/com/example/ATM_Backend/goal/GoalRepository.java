package com.example.ATM_Backend.goal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByUserNameAndAppNameAndDate(String userName, String appName, String date);
    List<Goal> findByUserName(String userName);
}
