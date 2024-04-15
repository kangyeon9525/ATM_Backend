package com.example.ATM_Backend.appUser.controller;


import com.example.ATM_Backend.appUser.dto.AppUserCreateForm;
import com.example.ATM_Backend.appUser.service.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!appUserCreateForm.getPassword1().equals(appUserCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        try{
            appUserService.create(appUserCreateForm.getUsername(), appUserCreateForm.getEmail(),
                    appUserCreateForm.getPassword1(), appUserCreateForm.getName(),
                    appUserCreateForm.getNickname(), appUserCreateForm.getAge(),
                    appUserCreateForm.getGender(), appUserCreateForm.getJob());
        }catch (DataIntegrityViolationException e) { //사용자 ID, 이메일 주소, 닉네임이 이미 존재할 경우
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자 입니다.");
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
}
