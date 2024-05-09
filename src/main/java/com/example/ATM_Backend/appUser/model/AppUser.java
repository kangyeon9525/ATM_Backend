package com.example.ATM_Backend.appUser.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

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
}