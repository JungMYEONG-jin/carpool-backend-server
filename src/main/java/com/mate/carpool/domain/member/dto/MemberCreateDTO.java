package com.mate.carpool.domain.member.dto;

import com.mate.carpool.domain.member.aggregate.Member;
import com.mate.carpool.domain.member.aggregate.MemberType;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class MemberCreateDTO {

    private String email;
    private String password;
    private String username;

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .username(username)
                .type(MemberType.PASSENGER)
                .profileImageUrl(null)
                .build();
    }

    public MemberCreateDTO(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }
}
