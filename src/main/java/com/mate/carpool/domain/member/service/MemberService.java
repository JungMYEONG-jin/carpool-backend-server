package com.mate.carpool.domain.member.service;

import com.mate.carpool.domain.member.dto.LoginDTO;
import com.mate.carpool.domain.member.dto.MemberCreateDTO;
import com.mate.carpool.web.auth.dto.TokenResponseDTO;

public interface MemberService {
    void create(MemberCreateDTO dto);
    TokenResponseDTO login(LoginDTO dto);
}
