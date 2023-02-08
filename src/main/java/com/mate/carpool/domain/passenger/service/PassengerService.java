package com.mate.carpool.domain.passenger.service;

import com.mate.carpool.domain.carpool.aggregate.Carpool;
import com.mate.carpool.domain.carpool.aggregate.CarpoolId;
import com.mate.carpool.domain.carpool.repository.CarpoolRepository;
import com.mate.carpool.domain.member.aggregate.Member;
import com.mate.carpool.domain.member.aggregate.MemberType;
import com.mate.carpool.domain.member.repository.MemberRepository;
import com.mate.carpool.domain.passenger.aggregate.Passenger;
import com.mate.carpool.domain.passenger.aggregate.PassengerId;
import com.mate.carpool.domain.passenger.aggregate.PassengerStatus;
import com.mate.carpool.domain.passenger.repository.PassengerRepository;
import com.mate.carpool.shared.exception.CustomHttpException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PassengerService {
    private final PassengerRepository passengerRepository;
    private final MemberRepository memberRepository;
    private final CarpoolRepository carpoolRepository;

    @Transactional
    public void ride(String email, String carpoolId) {
        Member member = memberRepository.findByCredentialEmail(email)
                .orElseThrow(() -> new CustomHttpException(HttpStatus.NOT_FOUND, "사용자 정보를 찾을 수 없습니다."));
        if (!member.getType().equals(MemberType.PASSENGER))
            throw new CustomHttpException(HttpStatus.FORBIDDEN, "탑승자 type 사용자만 신청할 수 있습니다.");
        if (carpoolRepository.existActiveCarpoolForPassenger(member.getId()))
            throw new CustomHttpException(HttpStatus.CONFLICT, "이미 탑승하고 있는 카풀이 있습니다.");

        Carpool carpool = carpoolRepository.findById(new CarpoolId(carpoolId))
                .orElseThrow(() -> new CustomHttpException(HttpStatus.NOT_FOUND, "해당하는 카풀을 찾을 수 없습니다."));
        carpool.ride();
        Passenger passenger = member.createPassenger(carpool.getId());
        passenger.getId().generate();
        passengerRepository.save(passenger);
    }

    @Transactional
    public void delete(String email, String passengerId) {
        Member member = memberRepository.findByCredentialEmail(email)
                .orElseThrow(() -> new CustomHttpException(HttpStatus.NOT_FOUND, "사용자 정보를 찾을 수 없습니다."));
        Passenger passenger = passengerRepository.findById(new PassengerId(passengerId))
                .orElseThrow(()-> new CustomHttpException(HttpStatus.NOT_FOUND, "탑승자 정보를 찾을 수 없습니다."));
        Carpool carpool = carpoolRepository.findById(passenger.getCarpoolId())
                .orElseThrow(()-> new CustomHttpException(HttpStatus.NOT_FOUND, "해당하는 카풀 정보를 찾을 수 없습니다."));

        if(!passenger.getStatus().equals(PassengerStatus.COMMON))
            throw new CustomHttpException(HttpStatus.CONFLICT, "이미 취소되었거나 퇴출된 패신저 입니다.");

        switch(member.getType()){
            case DRIVER:
                // 자신이 생성한 카풀의 탑승자를 퇴출합니다.
                if(!carpool.getCreatorId().equals(member.getId()))
                    throw new CustomHttpException(HttpStatus.FORBIDDEN, "해당 카풀에 대해서 퇴출 권한이 없습니다.");
                carpool.quit();
                passenger.kicked();
                break;
            case PASSENGER:
                // 자신을 현재 카풀에서 예약 취소합니다.
                carpool.quit();
                passenger.cancel();
                break;
            default:
                throw new CustomHttpException(HttpStatus.FORBIDDEN, "드라이버와 패신저가 아닌 사용하는 해당 api를 사용하실 수 없습니다.");
        }

    }
}
