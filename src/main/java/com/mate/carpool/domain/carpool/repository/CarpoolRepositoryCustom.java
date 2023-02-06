package com.mate.carpool.domain.carpool.repository;

import com.mate.carpool.domain.member.aggregate.MemberId;

public interface CarpoolRepositoryCustom {
    /**
     * 자신이 생성했고 해당 카풀이 여전히 유효한지(BEFORE, ING 상태인지) 찾아보는 인터페이스
     * @param creatorId 카풀을 생성한 사람의 member id
     * @return 만약 자신의 카풀이 아직 살아있다면 true, 아니라면 false 를 반환
     */
    boolean existActiveCarpool(MemberId creatorId);
}
