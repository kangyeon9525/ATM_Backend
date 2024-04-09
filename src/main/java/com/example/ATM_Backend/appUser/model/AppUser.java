package com.example.ATM_Backend.appUser.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    private String name;

    @Column(unique = true)
    private String nickname;

    private Integer age;
    private String gender;
    private String job;
}
