package com.example.ATM_Backend.Badge.service;

import com.example.ATM_Backend.appUser.model.AppUser;
import com.example.ATM_Backend.appUser.repository.AppUserRepository;
import com.example.ATM_Backend.Badge.model.Badge;
import com.example.ATM_Backend.Badge.model.UserBadge;
import com.example.ATM_Backend.Badge.repository.BadgeRepository;
import com.example.ATM_Backend.Badge.repository.UserBadgeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class BadgeServiceTest {

    @Autowired
    private BadgeService badgeService;

    @MockBean
    private AppUserRepository appUserRepository;

    @MockBean
    private BadgeRepository badgeRepository;

    @MockBean
    private UserBadgeRepository userBadgeRepository;

    private AppUser user;

    @BeforeEach
    public void setUp() {
        user = new AppUser();
        user.setId(12L);
        user.setUsername("111");
        user.setContinuous(3);
        Mockito.when(appUserRepository.findByUsername("111")).thenReturn(Optional.of(user));
        Mockito.when(appUserRepository.findAll()).thenReturn(List.of(user));

        Badge badge = new Badge();
        badge.setId(4L);
        badge.setCriteria(3);
        badge.setDescription("벌써 3일 째 출석했어요! 뇌가 맑아지는 것 같은 느낌이 들지 않나요?");
        badge.setName("🛳 순항 중");
        badge.setImage_url("https://atm-badge-logos.s3.ap-northeast-2.amazonaws.com/badge_logo/smooth_sailing.png");
        Mockito.when(badgeRepository.findAll()).thenReturn(List.of(badge));
        Mockito.when(badgeRepository.findById(4L)).thenReturn(Optional.of(badge));
    }

    @Test
    public void testCheckAndAwardBadgesBasedOnAttendance() {
        // Given: User has 3 continuous days of attendance
        user.setContinuous(3);

        // When: BadgeService checks and awards badges based on attendance
        badgeService.checkAndAwardBadgesBasedOnAttendance();

        // Then: A new UserBadge entry should be created
        Mockito.verify(userBadgeRepository, Mockito.times(1)).save(any(UserBadge.class));

        // Capture the UserBadge that was saved
        ArgumentCaptor<UserBadge> userBadgeCaptor = ArgumentCaptor.forClass(UserBadge.class);
        Mockito.verify(userBadgeRepository).save(userBadgeCaptor.capture());

        UserBadge savedUserBadge = userBadgeCaptor.getValue();

        // Verify that the saved UserBadge has the correct user and badge
        assertTrue(savedUserBadge.getUser().equals(user));
        assertTrue(savedUserBadge.getBadge().equals(badgeRepository.findById(4L).get()));

        // Mock the behavior of existsByUserAndBadge method
        Mockito.when(userBadgeRepository.existsByUserAndBadge(user, savedUserBadge.getBadge())).thenReturn(true);

        // Verify the UserBadge exists in the repository
        assertTrue(userBadgeRepository.existsByUserAndBadge(user, savedUserBadge.getBadge()));
    }
}
