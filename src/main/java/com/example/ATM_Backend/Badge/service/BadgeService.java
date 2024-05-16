package com.example.ATM_Backend.Badge.service;

import com.example.ATM_Backend.Badge.dto.BadgeDTO;  // BadgeDTO를 import
import com.example.ATM_Backend.Badge.dto.UserBadgeDTO;  // UserBadgeDTO를 import
import com.example.ATM_Backend.Badge.model.Badge;  // Badge 엔티티를 import
import com.example.ATM_Backend.Badge.model.UserBadge;  // UserBadge 엔티티를 import
import com.example.ATM_Backend.Badge.repository.BadgeRepository;  // BadgeRepository를 import
import com.example.ATM_Backend.Badge.repository.UserBadgeRepository;  // UserBadgeRepository를 import
import com.example.ATM_Backend.appUser.model.AppUser;  // AppUser 엔티티를 import
import com.example.ATM_Backend.appUser.repository.AppUserRepository;  // AppUserRepository를 import
import lombok.RequiredArgsConstructor;  // Lombok의 RequiredArgsConstructor 어노테이션을 import
import org.springframework.stereotype.Service;  // Spring의 Service 어노테이션을 import

import java.util.List;  // List를 import
import java.util.stream.Collectors;  // Collectors를 import

@RequiredArgsConstructor  // Lombok을 사용하여 생성자를 자동으로 생성
@Service  // 이 클래스가 서비스 레이어의 컴포넌트임을 명시
public class BadgeService {
    private final AppUserRepository appUserRepository;  // 사용자 저장소 주입
    private final BadgeRepository badgeRepository;  // 배지 저장소 주입
    private final UserBadgeRepository userBadgeRepository;  // 사용자 배지 저장소 주입

    // 사용자에게 배지를 부여하는 메서드
    public void checkAndAwardBadge(String username, Long badgeId) {
        AppUser user = appUserRepository.findByUsername(username).orElse(null);  // 사용자 조회
        Badge badge = badgeRepository.findById(badgeId).orElse(null);  // 배지 조회
        if (user != null && badge != null) {  // 사용자와 배지가 모두 존재하는 경우
            UserBadge userBadge = new UserBadge();  // 새로운 UserBadge 객체 생성
            userBadge.setUser(user);  // UserBadge에 사용자 설정
            userBadge.setBadge(badge);  // UserBadge에 배지 설정
            userBadge.setAchieved(true);  // 배지 획득 여부 설정
            userBadgeRepository.save(userBadge);  // UserBadge 저장
        }
    }

    // 사용자의 배지 목록을 가져오는 메서드
    public List<UserBadgeDTO> getUserBadges(String username) {
        AppUser user = appUserRepository.findByUsername(username).orElse(null);  // 사용자 조회
        if (user == null) {  // 사용자가 없는 경우
            return null;  // null 반환
        }
        List<UserBadge> userBadges = userBadgeRepository.findByUser(user);  // 사용자의 배지 목록 조회
        return userBadges.stream()  // Stream으로 변환
                .map(this::convertToDTO)  // UserBadge를 UserBadgeDTO로 변환
                .collect(Collectors.toList());  // 리스트로 수집
    }

    // UserBadge를 UserBadgeDTO로 변환하는 메서드
    private UserBadgeDTO convertToDTO(UserBadge userBadge) {
        BadgeDTO badgeDTO = new BadgeDTO(
                userBadge.getBadge().getId(),  // 배지 ID
                userBadge.getBadge().getName(),  // 배지 이름
                userBadge.getBadge().getDescription(),  // 배지 설명
                userBadge.getBadge().getCriteria()  // 배지 기준
        );
        return new UserBadgeDTO(userBadge.getId(), badgeDTO, userBadge.isAchieved());  // UserBadgeDTO 반환
    }
}
