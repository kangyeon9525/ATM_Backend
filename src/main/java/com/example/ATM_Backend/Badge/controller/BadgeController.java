package com.example.ATM_Backend.Badge.controller;

import com.example.ATM_Backend.Badge.dto.UserBadgeDTO;  // UserBadgeDTO를 import
import com.example.ATM_Backend.Badge.service.BadgeService;  // BadgeService를 import
import io.swagger.v3.oas.annotations.Operation;  // Swagger의 Operation 주석을 import
import lombok.RequiredArgsConstructor;  // Lombok의 RequiredArgsConstructor 주석을 import
import org.springframework.http.ResponseEntity;  // ResponseEntity를 import
import org.springframework.web.bind.annotation.*;

import java.util.List;  // List를 import

@RequiredArgsConstructor  // Lombok을 사용하여 생성자를 자동으로 생성
@RestController  // RESTful 컨트롤러로 설정
@RequestMapping("/api/badges")  // 이 클래스의 기본 URL 설정
public class BadgeController {
    private final BadgeService badgeService;  // BadgeService 주입

    // 사용자에게 배지를 부여하는 엔드포인트
    @PostMapping("/{username}/award/{badgeId}")  // HTTP POST 메서드로 설정
    public ResponseEntity<Void> awardBadge(@PathVariable String username, @PathVariable Long badgeId) {
        badgeService.checkAndAwardBadge(username, badgeId);  // 배지를 부여하는 서비스 호출
        return ResponseEntity.ok().build();  // 성공적으로 수행되었음을 나타내는 응답 반환
    }

    // 사용자가 소유한 배지 목록을 가져오는 엔드포인트
    @GetMapping("/{username}")  // HTTP GET 메서드로 설정
    public ResponseEntity<List<UserBadgeDTO>> getUserBadges(@PathVariable String username) {
        List<UserBadgeDTO> userBadges = badgeService.getUserBadges(username);  // 사용자의 배지 목록을 가져오는 서비스 호출
        if (userBadges == null) {  // 배지 목록이 없는 경우
            return ResponseEntity.notFound().build();  // 404 Not Found 응답 반환
        }
        return ResponseEntity.ok(userBadges);  // 배지 목록과 함께 성공 응답 반환
    }
}
