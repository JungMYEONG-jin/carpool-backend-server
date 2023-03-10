package com.mate.carpool.domain.carpool.aggregate;

import com.mate.carpool.domain.member.aggregate.MemberId;
import com.mate.carpool.domain.shared.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "carpool")
public class Carpool extends BaseTimeEntity {
    // 카풀 uuid
    @EmbeddedId
    private CarpoolId id;

    // 모집 인원
    @Column(name = "recruit_person")
    private Integer recruitPerson;

    // 카카오톡 오픈 채팅
    @Column(name = "open_chat_url")
    private String openChatUrl;

    // 출발지
    @Embedded
    private Departure departure;

    // 도착지
    @Embedded
    private Arrival arrival;

    // 카풀의 현재 상태(BEFORE, ING, CANCEL, AFTER)
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private CarpoolStatus status;

    // 카풀 생성자 ID
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "value", column = @Column(name = "creator_id"))})
    private MemberId creatorId;

    // 탑승 정보(장소와 가격)
    @Embedded
    private Boarding boarding;
}
