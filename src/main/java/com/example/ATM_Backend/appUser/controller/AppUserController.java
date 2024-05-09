package com.example.ATM_Backend.appUser.controller;

import com.example.ATM_Backend.appUser.model.AppUser;
import com.example.ATM_Backend.appUser.model.Role;
import com.example.ATM_Backend.appUser.repository.AppUserRepository;
import com.example.ATM_Backend.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AppUserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AppUserRepository appUserRepository;

    //회원가입
    @Operation(summary = "회원 가입")
    @ApiResponse(responseCode = "200", description = "성공적으로 회원가입 완료")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> user) {
        // 입력 값이 공백인지 검사
        if (!StringUtils.hasText(user.get("username")) ||
                !StringUtils.hasText(user.get("email")) ||
                !StringUtils.hasText(user.get("password")) ||
                !StringUtils.hasText(user.get("name")) ||
                !StringUtils.hasText(user.get("nickname")) ||
                !StringUtils.hasText(user.get("age")) ||
                !StringUtils.hasText(user.get("gender")) ||
                !StringUtils.hasText(user.get("job"))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All fields must be filled.");
        }
        // 이메일 형식 검사
        if (!user.get("email").matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email format.");
        }

        if (appUserRepository.findByUsername(user.get("username")).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists.");
        } else if (appUserRepository.findByEmail(user.get("email")).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists.");
        } else if (appUserRepository.findByNickname(user.get("nickname")).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nickname already exists.");
        }
        AppUser newUser = AppUser.builder()
                .username(user.get("username"))
                .email(user.get("email"))
                .password(passwordEncoder.encode(user.get("password")))
                .name(user.get("name"))
                .nickname(user.get("nickname"))
                .age(Integer.valueOf(user.get("age")))
                .gender(user.get("gender"))
                .job(user.get("job"))
                .role(Role.ROLE_MEMBER)  // 최초 가입시 USER로 설정
                .build();
        appUserRepository.save(newUser);
        return ResponseEntity.ok(String.valueOf(newUser.getId()));
    }

    //로그인
    @Operation(summary = "로그인")
    @ApiResponse(responseCode = "200", description = "로그인 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        AppUser appUser = appUserRepository.findByUsername(user.get("username"))
                .orElseThrow(() -> new IllegalArgumentException("가입 되지 않은 ID입니다."));
        if (!passwordEncoder.matches(user.get("password"), appUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ID 또는 비밀번호가 맞지 않습니다.");
        }
        return jwtTokenProvider.createToken(appUser.getUsername(), appUser.getRole());
    }

    //회원탈퇴
    @Operation(summary = "회원 탈퇴")
    @ApiResponse(responseCode = "200", description = "성공적으로 회원 탈퇴 완료")
    @DeleteMapping("/users/{username}/delete")
    public ResponseEntity<String> deleteUser(@RequestHeader("Authorization") String token) {
        String username = jwtTokenProvider.getUserPK(token.substring(7)); // "Bearer "를 제거한 토큰에서 사용자명 추출
        return appUserRepository.findByUsername(username)
                .map(user -> {
                    appUserRepository.delete(user);
                    return ResponseEntity.ok("계정이 삭제되었습니다.");
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다."));
    }

    @Operation(summary = "로그아웃")
    @ApiResponse(responseCode = "200", description = "성공적으로 로그아웃 완료")
    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        // 클라이언트 측에서 토큰을 삭제해야 합니다.
        return ResponseEntity.ok("로그아웃되었습니다. 클라이언트에서 사용 중인 토큰을 삭제해주세요.");
    }

}
