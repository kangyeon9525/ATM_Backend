package com.example.ATM_Backend.appUser.controller;


import com.example.ATM_Backend.appUser.model.AppUserCreateForm;
import com.example.ATM_Backend.appUser.service.AppUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/signup")
    public String signup(AppUserCreateForm appUserCreateForm) {
        return "signup_form";
    }

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

        appUserService.create(appUserCreateForm.getUsername(), appUserCreateForm.getEmail(),
                appUserCreateForm.getPassword1(), appUserCreateForm.getName(),
                appUserCreateForm.getNickname(), appUserCreateForm.getAge(),
                appUserCreateForm.getGender(), appUserCreateForm.getJob());

        return "redirect:/";
    }
}
