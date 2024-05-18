package com.example.ATM_Backend.appTime.repository;

import com.example.ATM_Backend.appTime.model.DailyUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyUsageRepository extends JpaRepository<DailyUsage, Long> {
    List<DailyUsage> findByUserNameAndDateAndAppName(String UserName, String date, String appName);
    List<DailyUsage> findByUserName(String userName);
}
