package com.example.ATM_Backend.appUser.model;

import lombok.Getter;

@Getter
public enum AppUserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"); // 관리자 ADMIN, 사용자 USER 상수 생성
    // ADMIN, USER 상수 값 변경 필요 X => @Setter 사용 X

    AppUserRole(String value) {
        this.value = value;
    }

    private String value;
}
