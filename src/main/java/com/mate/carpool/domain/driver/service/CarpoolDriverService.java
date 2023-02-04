package com.mate.carpool.domain.driver.service;

import com.mate.carpool.domain.driver.aggregate.Driver;
import com.mate.carpool.domain.driver.dto.DriverCreateDTO;
import com.mate.carpool.domain.driver.repository.DriverRepository;
import com.mate.carpool.domain.image.service.ImageService;
import com.mate.carpool.domain.member.aggregate.Member;
import com.mate.carpool.domain.member.aggregate.MemberType;
import com.mate.carpool.domain.member.repository.MemberRepository;
import com.mate.carpool.shared.exception.CustomHttpException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CarpoolDriverService {
    private final DriverRepository driverRepository;
    private final MemberRepository memberRepository;
    private final ImageService imageService;

    @Transactional
    public void create(String email, MultipartFile image, DriverCreateDTO dto) throws IOException {
        // 우리 회원인지 조회
        Member member = memberRepository.findByCredentialEmail(email)
                .orElseThrow(() -> new CustomHttpException(HttpStatus.BAD_REQUEST, "사용자 정보를 찾을 수 없습니다."));

        // 기존에 이미 회원 정보가 있는지 확인이 필요
        if (driverRepository.existsByMemberId(member.getId())) {
            throw new CustomHttpException(HttpStatus.CONFLICT, "이미 드라이버 정보가 있습니다.");
        }

        String imageUrl = imageService.upload(image);

        Driver driver = dto.toEntity(member, imageUrl);
        driver.getId().generate(); // id 생성
        member.changeType(MemberType.DRIVER);// member type DRIVER 로 변경
        driverRepository.save(driver);
    }
}
