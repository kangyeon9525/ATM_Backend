package com.example.ATM_Backend.Badge.service;

import com.example.ATM_Backend.Badge.dto.BadgeDTO;
import com.example.ATM_Backend.Badge.dto.UserBadgeDTO;
import com.example.ATM_Backend.appUser.model.AppUser;
import com.example.ATM_Backend.Badge.model.Badge;
import com.example.ATM_Backend.Badge.model.UserBadge;
import com.example.ATM_Backend.appUser.repository.AppUserRepository;
import com.example.ATM_Backend.Badge.repository.BadgeRepository;
import com.example.ATM_Backend.Badge.repository.UserBadgeRepository;
import com.example.ATM_Backend.goal.Goal;
import com.example.ATM_Backend.goal.GoalRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BadgeService {
    private final AppUserRepository appUserRepository;
    private final BadgeRepository badgeRepository;
    private final UserBadgeRepository userBadgeRepository;
    private final GoalRepository goalRepository;
    private static final Logger logger = LoggerFactory.getLogger(BadgeService.class);


    // 개별 배지를 수여하는 메서드
    public void checkAndAwardBadge(String username, Long badgeId) {
        AppUser user = appUserRepository.findByUsername(username).orElse(null);
        Badge badge = badgeRepository.findById(badgeId).orElse(null);
        if (user != null && badge != null) {
            UserBadge userBadge = new UserBadge();
            userBadge.setUser(user);
            userBadge.setBadge(badge);
            userBadge.setAchieved(true);
            userBadge.setUserName(user.getUsername());
            userBadgeRepository.save(userBadge);
        }
    }

    // 특정 유저의 모든 배지를 가져오는 메서드
    public List<UserBadgeDTO> getUserBadges(String username) {
        AppUser user = appUserRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return null;
        }
        List<UserBadge> userBadges = userBadgeRepository.findByUser(user);
        return userBadges.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // UserBadge를 UserBadgeDTO로 변환하는 메서드
    private UserBadgeDTO convertToDTO(UserBadge userBadge) {
        Badge badge = userBadge.getBadge();
        BadgeDTO badgeDTO = new BadgeDTO(
                badge.getId(),
                badge.getName(),
                badge.getDescription(),
                badge.getCriteria_attendance() != null ? badge.getCriteria_attendance() : 0,
                badge.getCriteria_goalusage(),
                badge.getImage_url()
        );
        return new UserBadgeDTO(userBadge.getId(), badgeDTO, userBadge.isAchieved());
    }

    @Scheduled(fixedRate = 300000) // 5분마다 실행
    public void checkAndAwardBadgesBasedOnAttendance() {
        logger.info("checkAndAwardBadgesBasedOnAttendance() 메서드가 호출되었습니다.");
        List<AppUser> users = appUserRepository.findAll();
        for (AppUser user : users) {
            List<Badge> badges = badgeRepository.findAll();
            for (Badge badge : badges) {
                Integer criteriaAttendance = badge.getCriteria_attendance();
                if (criteriaAttendance != null && user.getContinuous() != null && user.getContinuous() >= criteriaAttendance) {
                    if (!userBadgeRepository.existsByUserAndBadge(user, badge)) {
                        UserBadge userBadge = new UserBadge();
                        userBadge.setUser(user);
                        userBadge.setBadge(badge);
                        userBadge.setAchieved(true);
                        userBadge.setUserName(user.getUsername());
                        userBadgeRepository.save(userBadge);
                        logger.info("Badge awarded: {} to user: {}", badge.getName(), user.getUsername());
                    }
                }
            }
        }
    }

    // 목표 사용 기준으로 배지를 자동 수여하는 메서드
    @Scheduled(fixedRate = 300000) // 5분마다 실행
    public void checkAndAwardBadgesBasedOnGoalUsage() {
        logger.info("checkAndAwardBadgesBasedOnGoalUsage() 메서드가 호출되었습니다.");
        List<AppUser> users = appUserRepository.findAll();
        for (AppUser user : users) {
            List<Goal> goals = goalRepository.findByUserName(user.getUsername());
            for (Goal goal : goals) {
                List<Badge> badges = badgeRepository.findAll();
                for (Badge badge : badges) {
                    if (badge.getCriteria_goalusage() != null && goal.getHowLong() >= badge.getCriteria_goalusage()) {
                        if (!userBadgeRepository.existsByUserAndBadge(user, badge)) {
                            UserBadge userBadge = new UserBadge();
                            userBadge.setUser(user);
                            userBadge.setBadge(badge);
                            userBadge.setAchieved(true);
                            userBadge.setUserName(user.getUsername());
                            userBadgeRepository.save(userBadge);
                            logger.info("Badge awarded: {} to user: {}", badge.getName(), user.getUsername());
                        }
                    }
                }
            }
        }
    }
}
