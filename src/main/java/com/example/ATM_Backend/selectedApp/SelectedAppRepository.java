package com.example.ATM_Backend.selectedApp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SelectedAppRepository extends JpaRepository<SelectedApp, Long> {
    List<SelectedApp> findByUserNameAndAppNameAndPackageName(String UserName, String appName, String packageName);
    List<SelectedApp> findByUserName(String userName);
}
