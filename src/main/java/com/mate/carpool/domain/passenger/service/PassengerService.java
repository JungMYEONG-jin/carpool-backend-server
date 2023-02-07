package com.mate.carpool.domain.passenger.service;

import com.mate.carpool.domain.carpool.aggregate.Carpool;
import com.mate.carpool.domain.carpool.aggregate.CarpoolId;
import com.mate.carpool.domain.carpool.repository.CarpoolRepository;
import com.mate.carpool.domain.member.aggregate.Member;
import com.mate.carpool.domain.member.aggregate.MemberType;
import com.mate.carpool.domain.member.repository.MemberRepository;
import com.mate.carpool.domain.passenger.aggregate.Passenger;
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

        Passenger passenger = member.createPassenger(carpool.getId());
        passenger.getId().generate();
        passengerRepository.save(passenger);
    }
}
