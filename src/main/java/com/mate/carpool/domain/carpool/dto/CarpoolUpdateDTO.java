package com.mate.carpool.domain.carpool.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CarpoolUpdateDTO {
    private String carpoolId;
    private String departureArea;
    private LocalDateTime departureTime;
    private String arrivalArea;
    private String boardingPlace;
    private String openChatUrl;
    private Integer recruitPerson;
    private Integer boardingPrice;

    @Builder
    public CarpoolUpdateDTO(String carpoolId, String departureArea, LocalDateTime departureTime, String arrivalArea, String boardingPlace, String openChatUrl, Integer recruitPerson, Integer boardingPrice) {
        this.carpoolId = carpoolId;
        this.departureArea = departureArea;
        this.departureTime = departureTime;
        this.arrivalArea = arrivalArea;
        this.boardingPlace = boardingPlace;
        this.openChatUrl = openChatUrl;
        this.recruitPerson = recruitPerson;
        this.boardingPrice = boardingPrice;
    }
}
