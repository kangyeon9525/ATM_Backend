package com.example.ATM_Backend.appTime.repository;

import com.example.ATM_Backend.appTime.model.AppTime;
import com.example.ATM_Backend.appTime.model.WeeklyUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WeeklyUsageRepository extends JpaRepository<WeeklyUsage, Long> {
    List<WeeklyUsage> findByUserNameAndDateAndAppName(String UserName, String date, String appName);
}