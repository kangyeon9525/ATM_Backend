package com.example.ATM_Backend.appTime.service;

import com.example.ATM_Backend.appTime.model.AppTime;
import com.example.ATM_Backend.appTime.repository.AppTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppTimeService {

    private final AppTimeRepository appTimeRepository;

    public void saveOrUpdateAppTime(AppTime appTime) {
        List<AppTime> existingEntries = appTimeRepository.findByUserNameAndDateAndAppName(
                appTime.getUserName(), appTime.getDate(), appTime.getAppName());

        if (existingEntries.isEmpty()) { //기존행 없을때 post
            appTimeRepository.save(appTime);
        } else { //기존 행 있을때 put
            AppTime existingEntry = existingEntries.get(0);
            updateAppTime(existingEntry, appTime);
        }

    }

    private void updateAppTime(AppTime existingEntry, AppTime newEntry) { //시간대별 데이터 업데이트
        existingEntry.setHour00(newEntry.getHour00());
        existingEntry.setHour01(newEntry.getHour01());
        existingEntry.setHour02(newEntry.getHour02());
        existingEntry.setHour03(newEntry.getHour03());
        existingEntry.setHour04(newEntry.getHour04());
        existingEntry.setHour05(newEntry.getHour05());
        existingEntry.setHour06(newEntry.getHour06());
        existingEntry.setHour07(newEntry.getHour07());
        existingEntry.setHour08(newEntry.getHour08());
        existingEntry.setHour09(newEntry.getHour09());
        existingEntry.setHour10(newEntry.getHour10());
        existingEntry.setHour11(newEntry.getHour11());
        existingEntry.setHour12(newEntry.getHour12());
        existingEntry.setHour13(newEntry.getHour13());
        existingEntry.setHour14(newEntry.getHour14());
        existingEntry.setHour15(newEntry.getHour15());
        existingEntry.setHour16(newEntry.getHour16());
        existingEntry.setHour17(newEntry.getHour17());
        existingEntry.setHour18(newEntry.getHour18());
        existingEntry.setHour19(newEntry.getHour19());
        existingEntry.setHour20(newEntry.getHour20());
        existingEntry.setHour21(newEntry.getHour21());
        existingEntry.setHour22(newEntry.getHour22());
        existingEntry.setHour23(newEntry.getHour23());

        appTimeRepository.save(existingEntry);
    }

    public List<AppTime> getAppTimeByUserName(String userName) { //get
        List<AppTime> appTimes = appTimeRepository.findByUserName(userName);
        if (appTimes == null || appTimes.isEmpty()) {
            throw new IllegalArgumentException("No AppTime entries found for userName: " + userName);
        }
        return appTimes;
    }

    public boolean deleteAppTimeByUserName(String userName) { //delete
        List<AppTime> appTimes = appTimeRepository.findByUserName(userName);
        if (!appTimes.isEmpty()) {
            appTimeRepository.deleteAll(appTimes);
            return true;
        }
        return false;
    }
}
