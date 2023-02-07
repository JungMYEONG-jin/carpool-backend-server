package com.mate.carpool.web.carpool;

import com.mate.carpool.domain.carpool.service.CarpoolService;
import com.mate.carpool.shared.dto.CommonResponse;
import com.mate.carpool.web.carpool.dto.CarpoolCreateRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carpool")
public class CarpoolController {
    private final CarpoolService carpoolService;

    @PostMapping("")
    public ResponseEntity<CommonResponse> create(
            Principal principal,
            @Validated @RequestBody CarpoolCreateRequestDTO dto
    ) {
        String email = principal.getName();
        carpoolService.create(email, dto.toCreateDTO());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.of(HttpStatus.CREATED, "카풀을 성공적으로 생성했습니다."));
    }
}
