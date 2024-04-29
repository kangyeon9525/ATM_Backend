package com.example.ATM_Backend.appUser.service;

import com.example.ATM_Backend.appUser.model.AppUser;
import com.example.ATM_Backend.appUser.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUser create(String username, String email, String password, String name,
                          String nickname, Integer age, String gender, String job) {
        if (appUserRepository.findByUsername(username).isPresent()) {
            throw new DataIntegrityViolationException("Username already in use");
        }
        if (appUserRepository.findByEmail(email).isPresent()) {
            throw new DataIntegrityViolationException("Email already in use");
        }
        if (appUserRepository.findByNickname(nickname).isPresent()) {
            throw new DataIntegrityViolationException("Nickname already in use");
        }
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

    public void delete(String username) {
        // 회원 정보 삭제 메소드
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다"));
        // 찾은 사용자 정보를 삭제
        appUserRepository.delete(user);
    }
}
