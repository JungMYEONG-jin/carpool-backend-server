package com.mate.carpool.web.carpool;

import com.mate.carpool.domain.carpool.service.CarpoolService;
import com.mate.carpool.shared.dto.CommonResponse;
import com.mate.carpool.web.carpool.dto.CarpoolCreateRequestDTO;
import com.mate.carpool.web.carpool.dto.CarpoolShortResponseDTO;
import com.mate.carpool.web.carpool.dto.CarpoolUpdateRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carpool")
public class CarpoolController {
    private final CarpoolService carpoolService;

    /**
     * 카풀 생성
     */
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

    /**
     * 카풀 수정
     */
    @PutMapping("")
    public ResponseEntity<CommonResponse> update(
            @Validated @RequestBody CarpoolUpdateRequestDTO dto,
            Principal principal
    ) {
        String email = principal.getName();
        carpoolService.update(email, dto.toUpdateDTO());
        return ResponseEntity.ok(CommonResponse.of(HttpStatus.OK, "성공적으로 카풀 내용을 수정하였습니다."));
    }

    /**
     * 카풀 삭제
     */
    @DeleteMapping("/{carpoolId}")
    public ResponseEntity<CommonResponse> delete(
            @PathVariable(name = "carpoolId") String carpoolId,
            Principal principal
    ) {
        String email = principal.getName();
        carpoolService.delete(email, carpoolId);
        return ResponseEntity.ok(CommonResponse.of(HttpStatus.OK, "성공적으로 카풀이 삭제가 되었습니다."));
    }

    /**
     * 오늘 예약할 수 있는 전체 카풀 리스트 조회
     * 원래는 날짜 별로 조회가 가능하게 구현해야하지만 빠른 개발을 위해서 우선은 모든 카풀 리스트를 조회할 수 있도록 구현 후 수정하는 방식으로 진행
     */
    @GetMapping("")
    public ResponseEntity<List<CarpoolShortResponseDTO>> findAll() {
        List<CarpoolShortResponseDTO> list = carpoolService.getAvailableCarpoolList().stream()
                .map(CarpoolShortResponseDTO::from).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

}
