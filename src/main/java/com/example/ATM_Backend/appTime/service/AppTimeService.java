package com.example.ATM_Backend.appTime.service;

import com.example.ATM_Backend.appTime.model.AppTime;
import com.example.ATM_Backend.appTime.repository.AppTimeRepository;
import com.example.ATM_Backend.appUser.model.AppUser;
import com.example.ATM_Backend.appUser.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AppTimeService {

    @Autowired
    private AppTimeRepository appTimeRepository;

    public void saveOrUpdateAppTime(AppTime appTime) {
        // 기존 항목 확인
        List<AppTime> existingEntries = appTimeRepository.findByUserNameAndDateAndAppName(
                appTime.getUserName(), appTime.getDate(), appTime.getAppName());

        if (existingEntries.isEmpty()) {
            // 새로운 항목 추가
            appTimeRepository.save(appTime);
        } else {
            // 기존 항목 업데이트
            AppTime existingEntry = existingEntries.get(0);

            // user, date, appName이 모두 같을 경우에만 기존 행을 업데이트
            if (existingEntry.getUserName().equals(appTime.getUserName()) &&
                    existingEntry.getDate().equals(appTime.getDate()) &&
                    existingEntry.getAppName().equals(appTime.getAppName())) {

                // 시간대별로 업데이트
                existingEntry.setT_0(appTime.getT_0());
                existingEntry.setT_1(appTime.getT_1());
                existingEntry.setT_2(appTime.getT_2());
                existingEntry.setT_3(appTime.getT_3());
                existingEntry.setT_4(appTime.getT_4());
                existingEntry.setT_5(appTime.getT_5());
                existingEntry.setT_6(appTime.getT_6());
                existingEntry.setT_7(appTime.getT_7());
                existingEntry.setT_8(appTime.getT_8());
                existingEntry.setT_9(appTime.getT_9());
                existingEntry.setT_10(appTime.getT_10());
                existingEntry.setT_11(appTime.getT_11());
                existingEntry.setT_12(appTime.getT_12());
                existingEntry.setT_13(appTime.getT_13());
                existingEntry.setT_14(appTime.getT_14());
                existingEntry.setT_15(appTime.getT_15());
                existingEntry.setT_16(appTime.getT_16());
                existingEntry.setT_17(appTime.getT_17());
                existingEntry.setT_18(appTime.getT_18());
                existingEntry.setT_19(appTime.getT_19());
                existingEntry.setT_20(appTime.getT_20());
                existingEntry.setT_21(appTime.getT_21());
                existingEntry.setT_22(appTime.getT_22());
                existingEntry.setT_23(appTime.getT_23());

                appTimeRepository.save(existingEntry);
            } else {
                // user, date, appName 중 하나라도 다르면 새로운 항목 추가
                appTimeRepository.save(appTime);
            }
        }
    }
}