package com.mate.carpool.domain.carpool.service;

import com.mate.carpool.domain.carpool.aggregate.Carpool;
import com.mate.carpool.domain.carpool.aggregate.CarpoolId;
import com.mate.carpool.domain.carpool.dto.CarpoolCreateDTO;
import com.mate.carpool.domain.carpool.dto.CarpoolDetailDTO;
import com.mate.carpool.domain.carpool.dto.CarpoolShortDTO;
import com.mate.carpool.domain.carpool.dto.CarpoolUpdateDTO;
import com.mate.carpool.domain.carpool.repository.CarpoolRepository;
import com.mate.carpool.domain.driver.aggregate.Driver;
import com.mate.carpool.domain.driver.repository.DriverRepository;
import com.mate.carpool.domain.member.aggregate.Member;
import com.mate.carpool.domain.member.repository.MemberRepository;
import com.mate.carpool.domain.passenger.aggregate.Passenger;
import com.mate.carpool.domain.passenger.repository.PassengerRepository;
import com.mate.carpool.shared.exception.CustomHttpException;
import com.mate.carpool.domain.passenger.dto.PassengerDetailDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarpoolService {
    private final CarpoolRepository carpoolRepository;
    private final MemberRepository memberRepository;
    private final PassengerRepository passengerRepository;
    private final DriverRepository driverRepository;


    @Transactional
    public void create(String email, CarpoolCreateDTO dto) {
        Member member = memberRepository.findByCredentialEmail(email)
                .orElseThrow(() -> new CustomHttpException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        switch (member.getType()) {
            case DRIVER:
                // 기존에 이미 생성해 놓은 카풀이 있는지 확인하는 로직
                if (carpoolRepository.existActiveCarpool(member.getId())) {
                    throw new CustomHttpException(HttpStatus.CONFLICT, "이미 생성한 카풀이 있습니다.");
                }

                Carpool carpool = dto.toEntity(member.getId());
                carpool.getId().generate();
                carpoolRepository.save(carpool);
                break;
            default:
                throw new CustomHttpException(HttpStatus.FORBIDDEN, "드라이버가 아닌 사용자는 카풀을 생성할 수 없습니다.");
        }
    }

    @Transactional
    public void update(String email, CarpoolUpdateDTO dto) {
        Member member = memberRepository.findByCredentialEmail(email)
                .orElseThrow(() -> new CustomHttpException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));
        Carpool carpool = carpoolRepository.findById(new CarpoolId(dto.getCarpoolId()))
                .orElseThrow(() -> new CustomHttpException(HttpStatus.NOT_FOUND, "해당 카풀을 찾을 수 없습니다."));

        if (!member.getId().equals(carpool.getCreatorId())) {
            throw new CustomHttpException(HttpStatus.FORBIDDEN, "카풀을 변경할 권한이 없습니다.");
        }
        updateAll(carpool, dto);
    }

    void updateAll(Carpool carpool, CarpoolUpdateDTO dto) {
        carpool.updateDeparture(dto.getDepartureArea(), dto.getDepartureTime());
        carpool.updateArrival(dto.getArrivalArea());
        carpool.updateBoarding(dto.getBoardingPlace(), dto.getBoardingPrice());
        carpool.updateOpenChatUrl(dto.getOpenChatUrl());
        carpool.updateRecruitPerson(dto.getRecruitPerson());
    }

    @Transactional
    public void delete(String email, String carpoolId) {
        Member member = memberRepository.findByCredentialEmail(email)
                .orElseThrow(() -> new CustomHttpException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));
        Carpool carpool = carpoolRepository.findById(new CarpoolId(carpoolId))
                .orElseThrow(() -> new CustomHttpException(HttpStatus.NOT_FOUND, "해당 카풀을 찾을 수 없습니다."));
        if (!member.getId().equals(carpool.getCreatorId()))
            throw new CustomHttpException(HttpStatus.FORBIDDEN, "해당 카풀을 삭제할 권한이 없습니다.");

        // 해당 카풀의 모든 탑승자의 상태를 CANCEL 로 변경
        List<Passenger> passengers = passengerRepository.findAllByCarpoolId(carpool.getId());
        passengers.forEach(Passenger::cancel);
        carpool.delete();
    }


    @Transactional(readOnly = true)
    public List<CarpoolShortDTO> getAvailableCarpoolList() {
        return carpoolRepository.findAll().stream()
                .map(carpool -> {
                    Member member = memberRepository.findById(carpool.getCreatorId())
                            .orElseThrow(() -> new CustomHttpException(HttpStatus.NOT_FOUND, "사용자 정보를 찾을 수 없습니다."));
                    return CarpoolShortDTO.from(carpool, member);
                }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CarpoolDetailDTO getCarpoolDetail(String carpoolId) {
        Carpool carpool = carpoolRepository.findById(CarpoolId.of(carpoolId))
                .orElseThrow(() -> new CustomHttpException(HttpStatus.NOT_FOUND, "해당하는 카풀 정보를 찾을 수 없습니다."));
        Member member = memberRepository.findById(carpool.getCreatorId())
                .orElseThrow(() -> new CustomHttpException(HttpStatus.NOT_FOUND, "해당하는 사용자 정보를 찾을 수 없습니다."));
        Driver driver = driverRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new CustomHttpException(HttpStatus.NOT_FOUND, "해당하는 드라이버 정보를 찾을 수 없습니다."));

        log.info("카풀, 사용자, 드라이버 조회 완료");
        List<PassengerDetailDTO> passengers = passengerRepository.getPassengerDetail(carpool.getId());
        return CarpoolDetailDTO.create(carpool, member, driver, passengers);
    }
}
