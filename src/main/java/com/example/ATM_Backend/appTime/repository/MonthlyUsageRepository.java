package com.example.ATM_Backend.appTime.repository;

import com.example.ATM_Backend.appTime.model.MonthlyUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyUsageRepository extends JpaRepository<MonthlyUsage, Long> {
    List<MonthlyUsage> findByUserNameAndDateAndAppName(String UserName, String date, String appName);
    List<MonthlyUsage> findByUserName(String userName);
}
