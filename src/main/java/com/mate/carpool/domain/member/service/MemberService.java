package com.mate.carpool.domain.member.service;

import com.mate.carpool.domain.member.dto.LoginDTO;
import com.mate.carpool.domain.member.dto.MemberCreateDTO;
import com.mate.carpool.web.auth.dto.TokenResponseDTO;

import javax.servlet.http.HttpServletRequest;

public interface MemberService {
    void create(MemberCreateDTO dto);
    TokenResponseDTO login(LoginDTO dto);
    String logout(HttpServletRequest request);
    TokenResponseDTO reissue(HttpServletRequest request);
}
