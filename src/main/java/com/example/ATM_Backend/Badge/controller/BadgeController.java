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

    @PostMapping("/{username}/award/{badgeId}")
    public ResponseEntity<Void> awardBadge(@PathVariable String username, @PathVariable Long badgeId) {
        badgeService.checkAndAwardBadge(username, badgeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<UserBadgeDTO>> getUserBadges(@PathVariable String username) {
        List<UserBadgeDTO> userBadges = badgeService.getUserBadges(username);
        if (userBadges == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userBadges);
    }
}
