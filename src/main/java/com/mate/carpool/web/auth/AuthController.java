package com.mate.carpool.web.auth;

import com.mate.carpool.domain.member.dto.LoginDTO;
import com.mate.carpool.domain.member.dto.MemberCreateDTO;
import com.mate.carpool.domain.member.service.MemberService;
import com.mate.carpool.shared.dto.CommonResponse;
import com.mate.carpool.shared.dto.ResponseData;
import com.mate.carpool.web.auth.dto.LoginRequestDTO;
import com.mate.carpool.web.auth.dto.MemberCreateRequestDTO;
import com.mate.carpool.web.auth.dto.TokenResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.events.Event;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse> signup(@Validated @RequestBody MemberCreateRequestDTO dto) {
        memberService.create(new MemberCreateDTO(dto.getEmail(), dto.getPassword(), dto.getUsername()));
        return ResponseEntity.ok(new CommonResponse(HttpStatus.OK, "성공적으로 회원가입을 하였습니다."));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@Validated @RequestBody LoginRequestDTO dto) {
        TokenResponseDTO login = memberService.login(new LoginDTO(dto.getEmail(), dto.getPassword()));
        return ResponseEntity.ok(login);
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse> logout(HttpServletRequest request) {
        memberService.logout(request);
        return ResponseEntity.ok(new CommonResponse(HttpStatus.OK, "성공적으로 로그아웃을 하였습니다."));
    }
}
