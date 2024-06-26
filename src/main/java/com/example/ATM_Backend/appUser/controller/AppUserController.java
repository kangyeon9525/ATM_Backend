package com.example.ATM_Backend.appUser.controller;

import com.example.ATM_Backend.Badge.repository.UserBadgeRepository;
import com.example.ATM_Backend.appUser.model.AppUser;
import com.example.ATM_Backend.appUser.model.Role;
import com.example.ATM_Backend.appUser.repository.AppUserRepository;
import com.example.ATM_Backend.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AppUserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AppUserRepository appUserRepository;
    private final UserBadgeRepository userBadgeRepository;

    //회원가입
    @Operation(summary = "회원 가입")
    @ApiResponse(responseCode = "200", description = "성공적으로 회원가입 완료")
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "회원가입 정보",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            name = "registerExample",
                            value = "{\n" +
                                    "  \"username\": \"username\",\n" +
                                    "  \"email\": \"example@gmail.com\",\n" +
                                    "  \"password\": \"1234\",\n" +
                                    "  \"name\": \"홍길동\",\n" +
                                    "  \"nickname\": \"길동\",\n" +
                                    "  \"age\": \"20\",\n" +
                                    "  \"gender\": \"남자\",\n" +
                                    "  \"job\": \"학생\"\n" +
                                    "}",
                            description = "username: 사용자의 아이디, email: 이메일 주소, password: 비밀번호, name: 실명, nickname: 별명, age: 나이, gender: 성별, job: 직업"
                    )
            )
    )@RequestBody Map<String, String> user) {
        // 입력 값이 공백인지 검사
        if (!StringUtils.hasText(user.get("username")) ||
                !StringUtils.hasText(user.get("email")) ||
                !StringUtils.hasText(user.get("password")) ||
                !StringUtils.hasText(user.get("name")) ||
                !StringUtils.hasText(user.get("nickname")) ||
                !StringUtils.hasText(user.get("age")) ||
                !StringUtils.hasText(user.get("gender")) ||
                !StringUtils.hasText(user.get("job"))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "모든 입력창은 입력이 되어야합니다"));
        }
        // 이메일 형식 검사
        if (!user.get("email").matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "잘못된 이메일 형식입니다"));
        }

        if (appUserRepository.findByUsername(user.get("username")).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "해당 ID는 이미 존재합니다"));
        } else if (appUserRepository.findByEmail(user.get("email")).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "해당 이메일은 이미 존재합니다"));
        } else if (appUserRepository.findByNickname(user.get("nickname")).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "해당 별명은 이미 존재합니다"));
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
                .continuous(0)
                .loginDate(LocalDate.now())
                .initialSet(false)
                .build();
        appUserRepository.save(newUser);
        return ResponseEntity.ok(Map.of("id", newUser.getId(), "message", "회원가입을 성공했습니다"));
    }

    //로그인
    @Operation(summary = "로그인")
    @ApiResponse(responseCode = "200", description = "로그인 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "로그인 정보",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            name = "loginExample",
                            value = "{\n" +
                                    "  \"username\": \"username\",\n" +
                                    "  \"password\": \"1234\"\n" +
                                    "}",
                            description = "username: 사용자의 아이디, password: 비밀번호"
                    )
            )
    ) @RequestBody Map<String, String> user) {
        AppUser appUser = appUserRepository.findByUsername(user.get("username"))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "가입되지 않은 ID입니다"));
        if (!passwordEncoder.matches(user.get("password"), appUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "ID 또는 비밀번호가 맞지 않습니다"));
        }

        // 현재 날짜와 loginDate를 비교
        LocalDate currentDate = LocalDate.now();
        LocalDate previousDate = currentDate.minusDays(1);
        if (appUser.getLoginDate().isEqual(previousDate)) {
            appUser.incrementContinuous();
        } else {
            appUser.resetContinuous();
        }
        appUser.updateLoginDate(currentDate);

        // 최초 세팅여부 판단
        boolean isInitialSet = appUser.getInitialSet();
        if (!isInitialSet) {
            appUser.setInitialSet(true);
            appUserRepository.save(appUser); // Save only if initialSet was updated
            String token = jwtTokenProvider.createToken(appUser.getUsername(), appUser.getRole());
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("initialSet", false); // Return false for the first login
            return ResponseEntity.ok(response);
        }

        appUserRepository.save(appUser);

        String token = jwtTokenProvider.createToken(appUser.getUsername(), appUser.getRole());
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("initialSet", true); // Return true for subsequent logins
        return ResponseEntity.ok(response);
    }

    //회원탈퇴
    @Operation(summary = "회원 탈퇴")
    @ApiResponse(responseCode = "200", description = "성공적으로 회원 탈퇴 완료")
    @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.")
    @ApiResponse(responseCode = "401", description = "인증이 필요합니다.")
    @DeleteMapping("/users/{username}/delete")
    @Transactional
    public ResponseEntity<Map<String, String>> deleteUser(
            @Parameter(description = "Authorization Token", required = true,
                    examples = @ExampleObject(name = "Authorization 예시", value = "사용자 jwt 토큰"),
                    schema = @Schema(type = "string"))
            @RequestHeader(value = "Authorization") String token,
            @PathVariable ("username") String username) {
        String userPK = jwtTokenProvider.getUserPK(token.substring(7)); // "Bearer "를 제거한 토큰에서 사용자명 추출
        return appUserRepository.findByUsername(userPK)
                .map(user -> {
                    userBadgeRepository.deleteByUser(user); // user_badge 테이블에서 해당 사용자의 데이터 삭제
                    appUserRepository.delete(user); // app_user 테이블에서 해당 사용자 삭제
                    return ResponseEntity.ok(Map.of("message", "성공적으로 회원탈퇴 되었습니다"));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "해당 사용자가 보이지 않습니다.")));
    }

}
