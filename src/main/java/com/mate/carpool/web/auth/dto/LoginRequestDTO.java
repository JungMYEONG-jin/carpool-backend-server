package com.mate.carpool.web.auth.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
public class LoginRequestDTO {
    @NotNull(message = "이메일이 공백이면 안됩니다.")
    @Email(message = "이메일 형식이 잘못되었습니다.")
    private String email;

    @NotNull(message = "비밀번호가 공백이면 안됩니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",message = "최소 8 자, 대문자 하나 이상, 소문자 하나 및 숫자 하나를 입력하셔야합니다.")
    private String password;
}
