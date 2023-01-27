package com.mate.carpool.domain.member.aggregate;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
public class MemberCredential {
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public MemberCredential(String email, String password) {
        this.email = email;
        this.password = password;
    }

    protected MemberCredential() {
    }
}
