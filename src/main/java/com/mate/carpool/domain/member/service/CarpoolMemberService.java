package com.mate.carpool.domain.member.service;

import com.mate.carpool.domain.member.aggregate.Member;
import com.mate.carpool.domain.member.dto.MemberCreateDTO;
import com.mate.carpool.domain.member.repository.MemberRepository;
import com.mate.carpool.shared.exception.CustomHttpException;
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
}
