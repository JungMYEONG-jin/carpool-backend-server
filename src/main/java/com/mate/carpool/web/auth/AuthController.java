package com.mate.carpool.web.auth;

import com.mate.carpool.domain.member.dto.MemberCreateDTO;
import com.mate.carpool.domain.member.service.MemberService;
import com.mate.carpool.shared.dto.CommonResponse;
import com.mate.carpool.web.auth.dto.MemberCreateRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
