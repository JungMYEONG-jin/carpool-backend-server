package com.mate.carpool.domain.passenger.repository;

import com.mate.carpool.domain.carpool.aggregate.CarpoolId;
import com.mate.carpool.domain.passenger.aggregate.PassengerStatus;
import com.mate.carpool.domain.passenger.dto.PassengerDetailDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mate.carpool.domain.carpool.aggregate.QCarpool.carpool;
import static com.mate.carpool.domain.member.aggregate.QMember.member;
import static com.mate.carpool.domain.passenger.aggregate.QPassenger.passenger;

@Repository
@RequiredArgsConstructor
public class CustomPassengerRepositoryImpl implements CustomPassengerRepository {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<PassengerDetailDTO> getPassengerDetail(CarpoolId carpoolId) {
        return jpaQueryFactory.select(
                        Projections.constructor(
                                PassengerDetailDTO.class,
                                passenger.id,
                                member.username,
                                member.profileImageUrl
                        )).from(carpool)
                .innerJoin(passenger).on(passenger.carpoolId.eq(carpool.id))
                .innerJoin(member).on(passenger.memberId.eq(member.id))
                .where(carpool.id.eq(carpoolId))
                .where(passenger.status.eq(PassengerStatus.COMMON))
                .fetch();
    }
}
