package com.mate.carpool.domain.member.service;

import com.mate.carpool.domain.member.aggregate.Member;
import com.mate.carpool.domain.member.dto.LoginDTO;
import com.mate.carpool.domain.member.dto.MemberCreateDTO;
import com.mate.carpool.domain.member.repository.MemberRepository;
import com.mate.carpool.shared.exception.CustomHttpException;
import com.mate.carpool.web.auth.dto.TokenResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarpoolMemberService implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void create(MemberCreateDTO dto) {
        // 이메일이 이미 있는 사용자인지 확인
        if (memberRepository.existsByCredentialEmail(dto.getEmail())) {
            throw new CustomHttpException(HttpStatus.CONFLICT, "이미 있는 이메일 정보입니다.");
        }

        Member member = dto.toEntity(passwordEncoder);
        member.getId().generate();
        memberRepository.save(member);
    }

    @Override
    public TokenResponseDTO login(LoginDTO dto) {
        Member member = memberRepository.findByCredentialEmail(dto.getEmail()).orElseThrow(() -> new CustomHttpException(HttpStatus.UNAUTHORIZED, "이메일을 다시 입력해주세요."));
        if (!passwordEncoder.matches(dto.getPassword(), member.getCredential().getPassword())) {
            throw new CustomHttpException(HttpStatus.UNAUTHORIZED, "비밀번호를 다시 입력해주세요.");
        }
        return new TokenResponseDTO();
    }
}
