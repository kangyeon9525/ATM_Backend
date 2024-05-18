package com.example.ATM_Backend.appTime.service;

import com.example.ATM_Backend.appTime.model.MonthlyUsage;
import com.example.ATM_Backend.appTime.repository.MonthlyUsageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MonthlyUsageService {

    private final MonthlyUsageRepository monthlyUsageRepository;

    public void saveOrUpdateMonthlyUsage(MonthlyUsage monthlyUsage) {
        List<MonthlyUsage> existingEntries = monthlyUsageRepository.findByUserNameAndDateAndAppName(
                monthlyUsage.getUserName(), monthlyUsage.getDate(), monthlyUsage.getAppName());

        if (existingEntries.isEmpty()) { //기존행 없을때

            monthlyUsageRepository.save(monthlyUsage);

        } else { //기존 행 있을때
            MonthlyUsage existingEntry = existingEntries.get(0);
            updateMonthlyUsage(existingEntry, monthlyUsage);
        }

    }

    private void updateMonthlyUsage(MonthlyUsage existingEntry, MonthlyUsage newEntry) { //시간대별 데이터 업데이트
        existingEntry.setMonthlyUsage(newEntry.getMonthlyUsage());
        monthlyUsageRepository.save(existingEntry);
    }

    public List<MonthlyUsage> getMonthlyUsageByUserName(String userName) { //get
        List<MonthlyUsage> monthlyUsage = monthlyUsageRepository.findByUserName(userName);
        if (monthlyUsage == null || monthlyUsage.isEmpty()) {
            throw new IllegalArgumentException("No MonthlyUsage entries found for userName: " + userName);
        }
        return monthlyUsage;
    }

    public boolean deleteMonthlyUsageByUserName(String userName) { //delete
        List<MonthlyUsage> monthlyUsage = monthlyUsageRepository.findByUserName(userName);
        if (!monthlyUsage.isEmpty()) {
            monthlyUsageRepository.deleteAll(monthlyUsage);
            return true;
        }
        return false;
    }

}