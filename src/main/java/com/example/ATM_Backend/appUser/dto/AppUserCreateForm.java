package com.example.ATM_Backend.appUser.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUserCreateForm {
    @Size(min = 3, max = 25, message = "사용자 ID는 3자 이상 25자 이하입니다.")
    @NotEmpty(message = "사용자 ID는 필수항목입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String password2;

    @NotEmpty(message = "이메일 필수항목입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotEmpty(message = "본명은 필수항목입니다.")
    private String name;

    @NotEmpty(message = "별명은 필수항목입니다.")
    private String nickname;

    @NotNull(message = "본명은 필수항목입니다.")
    private Integer age;

    @NotEmpty(message = "성별은 필수항목입니다.")
    private String gender;

    @NotEmpty(message = "직업은 필수항목입니다.")
    private String job;
}
