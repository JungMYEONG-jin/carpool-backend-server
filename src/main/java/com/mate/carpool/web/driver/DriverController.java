package com.mate.carpool.web.driver;


import com.mate.carpool.domain.driver.dto.DriverCreateDTO;
import com.mate.carpool.domain.driver.service.CarpoolDriverService;
import com.mate.carpool.domain.image.service.ImageService;
import com.mate.carpool.shared.dto.CommonResponse;
import com.mate.carpool.shared.exception.CustomHttpException;
import com.mate.carpool.web.driver.dto.DriverCreateRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/driver")
public class DriverController {

    private final CarpoolDriverService driverService;
    private final ImageService imageService;

    @PostMapping("")
    public ResponseEntity<CommonResponse> create(
            Principal principal,
            @Validated @RequestPart(value = "dto") DriverCreateRequestDTO dto,
            @RequestPart(value = "image") MultipartFile image
    ) {
        String email = principal.getName();
        try {
            driverService.create(email, image, new DriverCreateDTO(dto.getCarNumber(), dto.getPhoneNumber()));
        } catch (IOException e) {
            throw new CustomHttpException(HttpStatus.INTERNAL_SERVER_ERROR, "image upload 도중에 예상치 못한 문제가 발생하였습니다.");
        }
        return ResponseEntity.ok(new CommonResponse(HttpStatus.OK, "성공적으로 드라이버 등록을 하였습니다."));
    }
}
