package com.mate.carpool.domain.passenger.aggregate;

import com.mate.carpool.domain.carpool.aggregate.CarpoolId;
import com.mate.carpool.domain.member.aggregate.MemberId;
import com.mate.carpool.domain.shared.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    protected Passenger() {
    }

    @Builder
    public Passenger(MemberId memberId, CarpoolId carpoolId, PassengerStatus status) {
        this.id = new PassengerId();
        this.memberId = memberId;
        this.carpoolId = carpoolId;
        this.status = status;
    }

    public void updateStatus(PassengerStatus status) {
        this.status = status;
    }

    public void kicked() {
        this.status = PassengerStatus.KICKED;
        this.deletedAt = LocalDateTime.now();
    }

    public void cancel() {
        this.status = PassengerStatus.CANCEL;
        this.deletedAt = LocalDateTime.now();
    }
}
