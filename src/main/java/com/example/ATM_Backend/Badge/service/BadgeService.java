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

    /**
     * 특정 사용자에게 지정된 배지를 수여하는 메서드
     *
     * @param username 사용자 이름
     * @param badgeId  배지 ID
     */
    public void checkAndAwardBadge(String username, Long badgeId) {
        AppUser user = appUserRepository.findByUsername(username).orElse(null);
        Badge badge = badgeRepository.findById(badgeId).orElse(null);
        if (user != null && badge != null) {
            UserBadge userBadge = new UserBadge();
            userBadge.setUser(user);
            userBadge.setBadge(badge);
            userBadge.setAchieved(true);
            userBadge.setUserName(user.getUsername()); // user_name 필드 설정
            userBadgeRepository.save(userBadge);
        }
    }

    /**
     * 특정 사용자의 모든 배지를 조회하는 메서드
     *
     * @param username 사용자 이름
     * @return 사용자 배지 목록
     */
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

    /**
     * UserBadge를 UserBadgeDTO로 변환하는 메서드
     *
     * @param userBadge UserBadge 객체
     * @return UserBadgeDTO 객체
     */
    private UserBadgeDTO convertToDTO(UserBadge userBadge) {
        BadgeDTO badgeDTO = new BadgeDTO(
                userBadge.getBadge().getId(),
                userBadge.getBadge().getName(),
                userBadge.getBadge().getDescription(),
                userBadge.getBadge().getCriteria_attendance(),
                userBadge.getBadge().getCriteria_goalusage(),
                userBadge.getBadge().getImage_url()
        );
        return new UserBadgeDTO(userBadge.getId(), badgeDTO, userBadge.isAchieved());
    }

    /**
     * 출석 기준으로 모든 사용자에게 배지를 수여하는 메서드
     */
    public void checkAndAwardBadgesBasedOnAttendance() {
        List<AppUser> users = appUserRepository.findAll();
        List<Badge> badges = badgeRepository.findAll();

        for (AppUser user : users) {
            Integer userContinuous = user.getContinuous() != null ? user.getContinuous() : 0;
            for (Badge badge : badges) {
                Integer criteriaAttendance = badge.getCriteria_attendance();
                if (criteriaAttendance != null && userContinuous >= criteriaAttendance) {
                    if (!userBadgeRepository.existsByUserAndBadge(user, badge)) {
                        UserBadge userBadge = new UserBadge();
                        userBadge.setUser(user);
                        userBadge.setBadge(badge);
                        userBadge.setAchieved(true);
                        userBadge.setUserName(user.getUsername()); // user_name 필드 설정
                        userBadgeRepository.save(userBadge);
                    }
                }
            }
        }
    }

    /**
     * 목표 사용량 기준으로 모든 사용자에게 배지를 수여하는 메서드
     */
    public void checkAndAwardBadgesBasedOnGoalUsage() {
        List<AppUser> users = appUserRepository.findAll();
        List<Badge> badges = badgeRepository.findAll();

        for (AppUser user : users) {
            List<Goal> goals = goalRepository.findByUserName(user.getUsername());

            for (Goal goal : goals) {
                Integer goalHowLong = goal.getHowLong();
                if (goalHowLong == null) continue;

                for (Badge badge : badges) {
                    Integer criteriaGoalUsage = badge.getCriteria_goalusage();
                    if (criteriaGoalUsage != null && goalHowLong >= criteriaGoalUsage) {
                        if (!userBadgeRepository.existsByUserAndBadge(user, badge)) {
                            UserBadge userBadge = new UserBadge();
                            userBadge.setUser(user);
                            userBadge.setBadge(badge);
                            userBadge.setAchieved(true);
                            userBadge.setUserName(user.getUsername()); // user_name 필드 설정
                            userBadgeRepository.save(userBadge);
                            System.out.println("Badge awarded: " + badge.getName() + " to user: " + user.getUsername());
                        }
                    }
                }
            }
        }
    }
}
