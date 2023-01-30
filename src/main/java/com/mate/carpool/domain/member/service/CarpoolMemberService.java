package com.mate.carpool.domain.member.service;

import com.mate.carpool.domain.member.aggregate.Member;
import com.mate.carpool.domain.member.dto.LoginDTO;
import com.mate.carpool.domain.member.dto.MemberCreateDTO;
import com.mate.carpool.domain.member.dto.TokenDTO;
import com.mate.carpool.domain.member.repository.MemberRepository;
import com.mate.carpool.shared.exception.CustomHttpException;
import com.mate.carpool.shared.security.provider.TokenProvider;
import com.mate.carpool.web.auth.dto.TokenResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CarpoolMemberService implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    //-------- for Security
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RedisTemplate redisTemplate;
    private static String refreshTokenPrefix = "RT:";
    //---------------------

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
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDTO tokenDTO = tokenProvider.generateToken(authenticate);
        redisTemplate.opsForValue().set(refreshTokenPrefix+authenticate.getName(), tokenDTO.getRefreshToken(), tokenDTO.getRefreshTokenExpiresIn(), TimeUnit.MILLISECONDS);
        return tokenDTO.toResponse();
    }

    @Override
    public String logout(HttpServletRequest request) {
        String accessToken = Optional.ofNullable(tokenProvider.parseBearerToken(request)).orElseThrow(() -> new CustomHttpException(HttpStatus.UNAUTHORIZED, "토큰 정보가 존재하지 않습니다."));
        if (!tokenProvider.validateToken(accessToken)) {
            throw new CustomHttpException(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다.");
        }
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String id = authentication.getName();
        if (redisTemplate.opsForValue().get("RT:" + id) != null) {
            // Delete Refresh Token
            redisTemplate.delete("RT:" + id);
        }
        // BlackList 로 저장하기
        Long expiration = tokenProvider.getExpiration(accessToken);
        redisTemplate.opsForValue()
                .set(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);
        return id;
    }
}
