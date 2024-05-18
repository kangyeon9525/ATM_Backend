package com.example.ATM_Backend.appTime.repository;

import com.example.ATM_Backend.appTime.model.AppTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppTimeRepository extends JpaRepository<AppTime, Long> {

    List<AppTime> findByUserNameAndDateAndAppName(String UserName, String date, String appName);
    List<AppTime> findByUserName(String userName);
}