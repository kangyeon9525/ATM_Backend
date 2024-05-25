package com.example.ATM_Backend.Badge.controller;

import com.example.ATM_Backend.Badge.dto.UserBadgeDTO;
import com.example.ATM_Backend.Badge.service.BadgeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/badges")
public class BadgeController {
    private final BadgeService badgeService;

    /**
     * 특정 사용자의 특정 배지를 수여하는 메서드
     * @param username 배지를 수여할 사용자 이름
     * @param badgeId 수여할 배지 ID
     * @return 성공 시 HTTP 200 응답을 반환
     */
    @PostMapping("/{username}/award/{badgeId}")
    public ResponseEntity<Void> awardBadge(@PathVariable String username, @PathVariable Long badgeId) {
        badgeService.checkAndAwardBadge(username, badgeId);
        return ResponseEntity.ok().build();
    }

    /**
     * 특정 사용자의 배지 목록을 가져오는 메서드
     * @param username 배지를 조회할 사용자 이름
     * @return 사용자의 배지 목록(UserBadgeDTO 리스트)
     */
    @GetMapping("/{username}")
    public ResponseEntity<List<UserBadgeDTO>> getUserBadges(@PathVariable String username) {
        List<UserBadgeDTO> userBadges = badgeService.getUserBadges(username);
        if (userBadges == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userBadges);
    }

    /**
     * 출석에 기반하여 배지를 체크하고 수여하는 메서드
     * @return 성공 시 HTTP 200 응답을 반환
     */
    @PostMapping("/check-and-award")
    public ResponseEntity<Void> checkAndAwardBadges() {
        badgeService.checkAndAwardBadgesBasedOnAttendance();
        return ResponseEntity.ok().build();
    }
}
