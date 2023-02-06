package com.mate.carpool.domain.carpool.repository;

import com.mate.carpool.domain.carpool.aggregate.CarpoolStatus;
import com.mate.carpool.domain.member.aggregate.MemberId;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.mate.carpool.domain.carpool.aggregate.QCarpool.carpool;

@Repository
@RequiredArgsConstructor
public class CarpoolRepositoryCustomImpl implements CarpoolRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean existActiveCarpool(MemberId creatorId) {
        return jpaQueryFactory.select(carpool)
                .from(carpool)
                .where(carpool.creatorId.eq(creatorId))
                .where(carpool.status.in(CarpoolStatus.BEFORE, CarpoolStatus.ING))
                .fetchFirst()!= null;
    }
}
