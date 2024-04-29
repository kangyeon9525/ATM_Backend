package com.example.ATM_Backend.appUser.controller;


import com.example.ATM_Backend.appUser.dto.AppUserCreateForm;
import com.example.ATM_Backend.appUser.service.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class AppUserController {
    private final AppUserService appUserService;

    @Operation(summary = "회원 가입 페이지 반환", description = "회원 가입을 위한 페이지를 반환합니다.")
    @GetMapping("/signup")
    public String signup(AppUserCreateForm appUserCreateForm) {
        return "signup_form";
    }

    @Operation(summary = "회원 가입 처리", description = "새로운 사용자의 회원 가입을 처리합니다.")
    @ApiResponse(responseCode = "200", description = "회원 가입 성공",
            content = @Content(schema = @Schema(implementation = AppUserCreateForm.class)))
    @ApiResponse(responseCode = "400", description = "입력 데이터 오류")
    @PostMapping("/signup")
    public String signup(@Valid AppUserCreateForm appUserCreateForm, BindingResult bindingResult) {
        boolean ErrChk = false;
        if (bindingResult.hasErrors()) {
            ErrChk = true;
        }
        if (appUserCreateForm.getUsername().trim().isEmpty()) {
            bindingResult.rejectValue("username", "signupFailed", "ID가 입력되지 않았습니다.");
            ErrChk = true;
        }

        if (appUserCreateForm.getEmail().trim().isEmpty()) {
            bindingResult.rejectValue("email", "signupFailed", "이메일이 입력되지 않았습니다.");
            ErrChk = true;
        }

        if (appUserCreateForm.getName().trim().isEmpty()) {
            bindingResult.rejectValue("name", "signupFailed", "이름이 입력되지 않았습니다.");
            ErrChk = true;
        }

        if (appUserCreateForm.getNickname().trim().isEmpty()) {
            bindingResult.rejectValue("nickname", "signupFailed", "닉네임이 입력되지 않았습니다.");
            ErrChk = true;
        }

        if (appUserCreateForm.getAge() == null || appUserCreateForm.getAge() < 0) {
            bindingResult.rejectValue("age", "signupFailed", "나이가 입력되지 않았습니다.");
            ErrChk = true;
        }
        if (appUserCreateForm.getGender().trim().isEmpty()) {
            bindingResult.rejectValue("gender", "signupFailed", "성별이 입력되지 않았습니다.");
            ErrChk = true;
        }
        if (appUserCreateForm.getJob().trim().isEmpty()) {
            bindingResult.rejectValue("job", "signupFailed", "직업이 입력되지 않았습니다.");
            ErrChk = true;
        }

        //비밀번호 일치 검사
        if (!appUserCreateForm.getPassword1().trim().isEmpty()) {
            if (!appUserCreateForm.getPassword1().equals(appUserCreateForm.getPassword2())) {
                bindingResult.rejectValue("password2", "passwordInCorrect",
                        "2개의 패스워드가 일치하지 않습니다.");
                ErrChk = true;
            }
        } else {
            bindingResult.rejectValue("password1", "signupFailed", "비밀번호가 입력되지 않았습니다.");
            ErrChk = true;
        }

        if (ErrChk) return "signup_form";

        try{
            appUserService.create(appUserCreateForm.getUsername(), appUserCreateForm.getEmail(),
                    appUserCreateForm.getPassword1(), appUserCreateForm.getName(),
                    appUserCreateForm.getNickname(), appUserCreateForm.getAge(),
                    appUserCreateForm.getGender(), appUserCreateForm.getJob());
        }catch (DataIntegrityViolationException e) { //사용자 ID, 이메일 주소, 닉네임이 이미 존재할 경우
            if (e.getMessage().contains("Username")) {
                bindingResult.rejectValue("username", "username.exists", "해당 사용자명은 사용 중입니다.");
            }
            if (e.getMessage().contains("Email")) {
                bindingResult.rejectValue("email", "email.exists", "해당 이메일은 사용 중입니다.");
            }
            if (e.getMessage().contains("Nickname")) {
                bindingResult.rejectValue("nickname", "nickname.exists", "해당 별명은 사용 중입니다.");
            }
            return "signup_form";
        }catch (Exception e) { // 그 이외의 오류 발생한 경우
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }

        return "redirect:/";
    }

    @Operation(summary = "로그인 페이지 반환", description = "로그인을 위한 페이지를 반환합니다.")
    @GetMapping("/login") // /user/login URL로 들어오는 GET 요청을 이 메서드가 처리
    public String login() {
        return "login_form";
    }

    @Operation(summary = "회원탈퇴 페이지 반환", description = "회원탈퇴를 위한 페이지를 반환합니다.")
    @GetMapping("/delete")
    public String deleteForm() {
        return "delete_form"; //회원 탈퇴 폼 페이지 이동
    }

    @Operation(summary = "회원탈퇴", description = "현재 유저의 계정을 회원탈퇴합니다.")
    @ApiResponse(responseCode = "200", description = "유저가 성공적으로 삭제되었습니다.")
    @ApiResponse(responseCode = "401", description = "로그인하지 않은 경우 권한이 없습니다.")
    @PostMapping("/delete")
    public String deleteUser() {
        // 현재 로그인된 사용자의 사용자명을 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 현재 로그인된 사용자의 사용자명을 가져옴
        String currentUsername = authentication.getName();

        if (authentication.isAuthenticated()) { // 사용자가 인증된 경우에만 삭제 수행
            try {
                appUserService.delete(currentUsername); // 사용자명을 기반으로 사용자 계정 삭제
                SecurityContextHolder.clearContext(); // 보안 컨텍스트를 클리어하여 사용자 로그아웃 처리
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.invalidate(); // 세션을 무효화하여 완전한 정리를 보장
                }
                return "redirect:/"; // 회원탈퇴 후 메인페이지로 리턴
            } catch (Exception e) {
                e.printStackTrace();
                return "delete_form"; // 오류 발생 시 삭제 폼으로 다시 리다이렉트
            }
        } else {
            return "redirect:/"; // 로그인 된 상태가 아니라면, 메인페이지로 리턴
        }
    }
}
