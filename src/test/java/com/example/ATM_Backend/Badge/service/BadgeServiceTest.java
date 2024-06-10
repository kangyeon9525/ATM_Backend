package com.example.ATM_Backend.Badge.service;

import com.example.ATM_Backend.appUser.model.AppUser;
import com.example.ATM_Backend.appUser.repository.AppUserRepository;
import com.example.ATM_Backend.Badge.model.Badge;
import com.example.ATM_Backend.Badge.model.UserBadge;
import com.example.ATM_Backend.Badge.repository.BadgeRepository;
import com.example.ATM_Backend.Badge.repository.UserBadgeRepository;
import com.example.ATM_Backend.goal.Goal;
import com.example.ATM_Backend.goal.GoalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BadgeServiceTest {

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private BadgeRepository badgeRepository;

    @Mock
    private UserBadgeRepository userBadgeRepository;

    @Mock
    private GoalRepository goalRepository;

    @InjectMocks
    private BadgeService badgeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCheckAndAwardBadgesBasedOnAttendance() {
        // Given
        AppUser user = new AppUser();
        user.setUsername("test_user");
        user.setContinuous(15); // Example continuous value

        Badge badge = new Badge();
        badge.setCriteria_attendance(10);
        badge.setName("Attendance Badge"); // Setting badge name

        when(appUserRepository.findAll()).thenReturn(Collections.singletonList(user));
        when(badgeRepository.findAll()).thenReturn(Collections.singletonList(badge));
        when(userBadgeRepository.existsByUserAndBadge(user, badge)).thenReturn(false);

        // When
        badgeService.checkAndAwardBadgesBasedOnAttendance();

        // Then
        verify(appUserRepository, times(1)).findAll();
        verify(userBadgeRepository, times(1)).save(any(UserBadge.class));
    }

    @Test
    public void testCheckAndAwardBadgesBasedOnGoalUsage() {
        // Given
        AppUser user = new AppUser();
        user.setUsername("test_user");

        Goal goal = new Goal();
        goal.setHowLong(31); // Example how_long value

        Badge badge = new Badge();
        badge.setCriteria_goalusage(30);
        badge.setName("Goal Usage Badge"); // Setting badge name

        when(appUserRepository.findAll()).thenReturn(Collections.singletonList(user));
        when(goalRepository.findByUserName("test_user")).thenReturn(Collections.singletonList(goal));
        when(badgeRepository.findAll()).thenReturn(Collections.singletonList(badge));
        when(userBadgeRepository.existsByUserAndBadge(user, badge)).thenReturn(false);

        // When
        badgeService.checkAndAwardBadgesBasedOnGoalUsage();

        // Then
        verify(appUserRepository, times(1)).findAll();
        verify(goalRepository, times(1)).findByUserName("test_user");
        verify(userBadgeRepository, times(1)).save(any(UserBadge.class));
    }
}
