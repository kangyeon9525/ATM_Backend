package com.example.ATM_Backend.appTime.service;

import com.example.ATM_Backend.appTime.model.DailyUsage;
import com.example.ATM_Backend.appTime.repository.DailyUsageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyUsageService {

    private final DailyUsageRepository dailyUsageRepository;

    public void saveOrUpdateDailyUsage(DailyUsage dailyUsage) {
        List<DailyUsage> existingEntries = dailyUsageRepository.findByUserNameAndDateAndAppName(
                dailyUsage.getUserName(), dailyUsage.getDate(), dailyUsage.getAppName());

        if (existingEntries.isEmpty()) { //기존행 없을때

            dailyUsageRepository.save(dailyUsage);

        } else { //기존 행 있을때
            DailyUsage existingEntry = existingEntries.get(0);
            updateDailyUsage(existingEntry, dailyUsage);
        }

    }

    private void updateDailyUsage(DailyUsage existingEntry, DailyUsage newEntry) { //시간대별 데이터 업데이트
        existingEntry.setDailyUsage(newEntry.getDailyUsage());
        dailyUsageRepository.save(existingEntry);
    }

}
