package com.example.ATM_Backend.Badge.service;

import com.example.ATM_Backend.Badge.dto.BadgeDTO;
import com.example.ATM_Backend.Badge.dto.UserBadgeDTO;
import com.example.ATM_Backend.Badge.model.Badge;
import com.example.ATM_Backend.Badge.model.UserBadge;
import com.example.ATM_Backend.Badge.repository.BadgeRepository;
import com.example.ATM_Backend.Badge.repository.UserBadgeRepository;
import com.example.ATM_Backend.appUser.model.AppUser;
import com.example.ATM_Backend.appUser.repository.AppUserRepository;
import com.example.ATM_Backend.goal.Goal;
import com.example.ATM_Backend.goal.GoalRepository;
import lombok.RequiredArgsConstructor;
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

    public void checkAndAwardBadge(String username, Long badgeId) {
        AppUser user = appUserRepository.findByUsername(username).orElse(null);
        Badge badge = badgeRepository.findById(badgeId).orElse(null);
        if (user != null && badge != null) {
            UserBadge userBadge = new UserBadge();
            userBadge.setUser(user);
            userBadge.setBadge(badge);
            userBadge.setAchieved(true);
            userBadgeRepository.save(userBadge);
        }
    }

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

    private UserBadgeDTO convertToDTO(UserBadge userBadge) {
        BadgeDTO badgeDTO = new BadgeDTO(
                userBadge.getBadge().getId(),
                userBadge.getBadge().getName(),
                userBadge.getBadge().getDescription(),
                userBadge.getBadge().getCriteria_attendance(), // 수정된 부분
                userBadge.getBadge().getImage_url()
        );
        return new UserBadgeDTO(userBadge.getId(), badgeDTO, userBadge.isAchieved());
    }

    public void checkAndAwardBadgesBasedOnAttendance() {
        List<AppUser> users = appUserRepository.findAll();
        for (AppUser user : users) {
            List<Badge> badges = badgeRepository.findAll();
            for (Badge badge : badges) {
                Integer continuous = user.getContinuous() != null ? user.getContinuous() : 0;
                if (continuous >= badge.getCriteria_attendance()) { // 수정된 부분
                    if (!userBadgeRepository.existsByUserAndBadge(user, badge)) {
                        UserBadge userBadge = new UserBadge();
                        userBadge.setUser(user);
                        userBadge.setBadge(badge);
                        userBadge.setAchieved(true);
                        userBadgeRepository.save(userBadge);
                    }
                }
            }
        }
    }

    public void checkAndAwardBadgesBasedOnGoalUsage() {
        List<AppUser> users = appUserRepository.findAll();
        for (AppUser user : users) {
            List<Goal> goals = goalRepository.findByUserNameAndOnGoing(user.getUsername(), 1);
            List<Badge> badges = badgeRepository.findAll();
            for (Goal goal : goals) {
                for (Badge badge : badges) {
                    if (badge.getCriteria_goalusage() > 0 && goal.getHowLong() >= badge.getCriteria_goalusage()) { // 수정된 부분
                        if (!userBadgeRepository.existsByUserAndBadge(user, badge)) {
                            UserBadge userBadge = new UserBadge();
                            userBadge.setUser(user);
                            userBadge.setBadge(badge);
                            userBadge.setAchieved(true);
                            userBadgeRepository.save(userBadge);
                        }
                    }
                }
            }
        }
    }
}
