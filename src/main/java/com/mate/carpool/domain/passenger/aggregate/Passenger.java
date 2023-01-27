package com.mate.carpool.domain.passenger.aggregate;

import com.mate.carpool.domain.carpool.aggregate.CarpoolId;
import com.mate.carpool.domain.member.aggregate.MemberId;
import com.mate.carpool.domain.shared.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "passenger")
public class Passenger extends BaseTimeEntity {
    // passenger uuid
    @EmbeddedId
    private PassengerId id;

    // 탑승자의 유저 정보
    @Embedded
    private MemberId memberId;

    // 탑승자의 카풀 uuid
    @Embedded
    private CarpoolId carpoolId;

    // 탑승자의 상태(COMMON, KICKED, CANCEL)
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private PassengerStatus status = PassengerStatus.COMMON;
}
