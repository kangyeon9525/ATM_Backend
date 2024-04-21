package com.example.ATM_Backend.appUser.service;

import com.example.ATM_Backend.appUser.model.AppUser;
import com.example.ATM_Backend.appUser.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUser create(String username, String email, String password, String name,
                          String nickname, Integer age, String gender, String job) {
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // BCryptPasswordEncoder 클래스를 사용하여 암호화하여 비밀번호를 저장
        user.setName(name);
        user.setNickname(nickname);
        user.setAge(age);
        user.setGender(gender);
        user.setJob(job);
        this.appUserRepository.save(user);
        return user;
    }
}
