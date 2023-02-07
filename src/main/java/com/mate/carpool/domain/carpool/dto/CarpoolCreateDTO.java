package com.mate.carpool.domain.carpool.dto;

import com.mate.carpool.domain.carpool.aggregate.Carpool;
import com.mate.carpool.domain.carpool.aggregate.CarpoolStatus;
import com.mate.carpool.domain.member.aggregate.MemberId;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CarpoolCreateDTO {
    // 출발 지역
    private String departureArea;
    // 출발 날짜 및 시간
    private LocalDateTime departureTime;
    // 도착 지역
    private String arrivalArea;
    // 탑승 장소
    private String boardingPlace;
    // 오픈 채팅방 링크
    private String openChatUrl;
    // 탑승 인원
    private Integer recruitPerson;
    // 인당 탑승 비용
    private Integer boardingPrice;

    @Builder
    public CarpoolCreateDTO(String departureArea, LocalDateTime departureTime, String arrivalArea, String boardingPlace, String openChatUrl, Integer recruitPerson, Integer boardingPrice) {
        this.departureArea = departureArea;
        this.departureTime = departureTime;
        this.arrivalArea = arrivalArea;
        this.boardingPlace = boardingPlace;
        this.openChatUrl = openChatUrl;
        this.recruitPerson = recruitPerson;
        this.boardingPrice = boardingPrice;
    }

    public Carpool toEntity(MemberId memberId){
        return Carpool.builder()
                .creatorId(memberId)
                .departureArea(departureArea)
                .boardingPlace(boardingPlace)
                .departureTime(departureTime)
                .boardingPrice(boardingPrice)
                .openChatUrl(openChatUrl)
                .recruitPerson(recruitPerson)
                .arrivalArea(arrivalArea)
                .status(CarpoolStatus.BEFORE)
                .build();
    }
}
