package com.example.ATM_Backend.Badge.service;

import com.example.ATM_Backend.Badge.dto.BadgeDTO;
import com.example.ATM_Backend.Badge.dto.UserBadgeDTO;
import com.example.ATM_Backend.appUser.model.AppUser;
import com.example.ATM_Backend.Badge.model.Badge;
import com.example.ATM_Backend.Badge.model.UserBadge;
import com.example.ATM_Backend.appUser.repository.AppUserRepository;
import com.example.ATM_Backend.Badge.repository.BadgeRepository;
import com.example.ATM_Backend.Badge.repository.UserBadgeRepository;
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

    /**
     * 특정 사용자의 특정 배지를 확인하고 수여하는 메서드
     * @param username 수여할 사용자 이름
     * @param badgeId 수여할 배지 ID
     */
    public void checkAndAwardBadge(String username, Long badgeId) {
        AppUser user = appUserRepository.findByUsername(username).orElse(null);
        Badge badge = badgeRepository.findById(badgeId).orElse(null);
        if (user != null && badge != null) {
            UserBadge userBadge = new UserBadge();
            userBadge.setUser(user);
            userBadge.setBadge(badge);
            userBadge.setAchieved(true); // 배지가 달성되었음을 설정
            userBadgeRepository.save(userBadge);
        }
    }

    /**
     * 특정 사용자의 배지 목록을 가져오는 메서드
     * @param username 배지를 조회할 사용자 이름
     * @return 사용자의 배지 목록(UserBadgeDTO 리스트)
     */
    public List<UserBadgeDTO> getUserBadges(String username) {
        AppUser user = appUserRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return null; // 사용자가 존재하지 않을 경우 null 반환
        }
        List<UserBadge> userBadges = userBadgeRepository.findByUser(user);
        return userBadges.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * UserBadge 객체를 UserBadgeDTO로 변환하는 메서드
     * @param userBadge 변환할 UserBadge 객체
     * @return 변환된 UserBadgeDTO 객체
     */
    private UserBadgeDTO convertToDTO(UserBadge userBadge) {
        BadgeDTO badgeDTO = new BadgeDTO(
                userBadge.getBadge().getId(),
                userBadge.getBadge().getName(),
                userBadge.getBadge().getDescription(),
                userBadge.getBadge().getCriteria(),
                userBadge.getBadge().getImage_url()
        );
        return new UserBadgeDTO(userBadge.getId(), badgeDTO, userBadge.isAchieved());
    }

    /**
     * 모든 사용자에 대해 출석에 기반하여 배지를 체크하고 수여하는 메서드
     */
    public void checkAndAwardBadgesBasedOnAttendance() {
        List<AppUser> users = appUserRepository.findAll();
        for (AppUser user : users) {
            List<Badge> badges = badgeRepository.findAll();
            for (Badge badge : badges) {
                Integer continuous = user.getContinuous() != null ? user.getContinuous() : 0;
                if (continuous >= badge.getCriteria()) {
                    if (!userBadgeRepository.existsByUserAndBadge(user, badge)) {
                        UserBadge userBadge = new UserBadge();
                        userBadge.setUser(user);
                        userBadge.setBadge(badge);
                        userBadge.setAchieved(true); // 배지가 달성되었음을 설정
                        userBadgeRepository.save(userBadge);
                    }
                }
            }
        }
    }
}
