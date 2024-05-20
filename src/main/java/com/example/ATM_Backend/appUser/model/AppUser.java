package com.example.ATM_Backend.appUser.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;
    private String nickname;

    @Email(message = "유효하지 않은 이메일 형식입니다.")
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
    private String name;
    private Integer age;
    private String gender;
    private String job;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer continuous = 0;  // 기본값을 0으로 설정

    @Column(nullable = false)
    private LocalDate loginDate = LocalDate.now(); // 날짜만 저장

    public void updateLoginDate(LocalDate newDate) {
        this.loginDate = newDate;
    }

    public void incrementContinuous() {
        this.continuous += 1;
    }

    public void resetContinuous() {
        this.continuous = 1;
    }
}
