package com.example.ATM_Backend.appTime.service;

import com.example.ATM_Backend.appTime.model.WeeklyUsage;
import com.example.ATM_Backend.appTime.repository.WeeklyUsageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeeklyUsageService {

    private final WeeklyUsageRepository weeklyUsageRepository;

    public void saveOrUpdateWeeklyUsage(WeeklyUsage weeklyUsage) {
        List<WeeklyUsage> existingEntries = weeklyUsageRepository.findByUserNameAndDateAndAppName(
                weeklyUsage.getUserName(), weeklyUsage.getDate(), weeklyUsage.getAppName());

        if (existingEntries.isEmpty()) { //기존행 없을때

            weeklyUsageRepository.save(weeklyUsage);

        } else { //기존 행 있을때
            WeeklyUsage existingEntry = existingEntries.get(0);
            updateWeeklyUsage(existingEntry, weeklyUsage);
        }

    }

    private void updateWeeklyUsage(WeeklyUsage existingEntry, WeeklyUsage newEntry) { //시간대별 데이터 업데이트
        existingEntry.setWeeklyUsage(newEntry.getWeeklyUsage());
        weeklyUsageRepository.save(existingEntry);
    }

    public List<WeeklyUsage> getWeeklyUsageByUserName(String userName) { //get
        List<WeeklyUsage> weeklyUsage = weeklyUsageRepository.findByUserName(userName);
        if (weeklyUsage == null || weeklyUsage.isEmpty()) {
            throw new IllegalArgumentException("No WeeklyUsage entries found for userName: " + userName);
        }
        return weeklyUsage;
    }

    public boolean deleteWeeklyUsageByUserName(String userName) { //delete
        List<WeeklyUsage> weeklyUsage = weeklyUsageRepository.findByUserName(userName);
        if (!weeklyUsage.isEmpty()) {
            weeklyUsageRepository.deleteAll(weeklyUsage);
            return true;
        }
        return false;
    }

}
