package com.example.ATM_Backend.selectedApp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SelectedAppService {
    private final SelectedAppRepository selectedAppRepository;

    public void saveOrUpdateSelectedApp(SelectedApp selectedApp) {
        List<SelectedApp> existingEntries = selectedAppRepository.findByUserNameAndAppNameAndPackageName(
                selectedApp.getUserName(), selectedApp.getAppName(), selectedApp.getPackageName());

        if (existingEntries.isEmpty()) { //기존행 없을때 post
            selectedAppRepository.save(selectedApp);
        } else { //기존 행 있을때 put
            SelectedApp existingEntry = existingEntries.get(0);
            updateSelectedApp(existingEntry, selectedApp);
        }
    }

    private void updateSelectedApp(SelectedApp existingEntry, SelectedApp newEntry) { //시간대별 데이터 업데이트
        existingEntry.setIsSelected(newEntry.getIsSelected());

        selectedAppRepository.save(existingEntry);
    }

    public List<SelectedApp> getSelectedAppByUserName(String userName) { //get
        List<SelectedApp> selectedApps = selectedAppRepository.findByUserName(userName);
        if (selectedApps == null || selectedApps.isEmpty()) {
            throw new IllegalArgumentException("No SelectedApp entries found for userName: " + userName);
        }
        return selectedApps;
    }

    public boolean deleteSelectedAppByUserName(String userName) { //delete
        List<SelectedApp> selectedApps = selectedAppRepository.findByUserName(userName);
        if (!selectedApps.isEmpty()) {
            selectedAppRepository.deleteAll(selectedApps);
            return true;
        }
        return false;
    }
}
