package com.mate.carpool.domain.carpool.dto;

import com.mate.carpool.domain.carpool.aggregate.Carpool;
import com.mate.carpool.domain.driver.aggregate.Driver;
import com.mate.carpool.domain.member.aggregate.Member;
import com.mate.carpool.web.carpool.dto.CarpoolShortResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CarpoolShortDTO {
    private String carpoolId;
    private String departureArea;
    private LocalDateTime departureTime;
    private String driverImageUrl;
    private Integer currentPerson;
    private Integer recruitPerson;
    private Integer boardingPrice;

    @Builder
    public CarpoolShortDTO(String carpoolId, String departureArea, LocalDateTime departureTime, String driverImageUrl, Integer currentPerson, Integer recruitPerson, Integer boardingPrice) {
        this.carpoolId = carpoolId;
        this.departureArea = departureArea;
        this.departureTime = departureTime;
        this.driverImageUrl = driverImageUrl;
        this.currentPerson = currentPerson;
        this.recruitPerson = recruitPerson;
        this.boardingPrice = boardingPrice;
    }

    public static CarpoolShortDTO from(Carpool carpool, Member member){
        return CarpoolShortDTO.builder()
                .carpoolId(carpool.getId().getValue())
                .departureArea(carpool.getDeparture().getArea())
                .departureTime(carpool.getDeparture().getTime())
                .driverImageUrl(member.getProfileImageUrl())
                .currentPerson(carpool.getCurrentPerson())
                .recruitPerson(carpool.getRecruitPerson())
                .boardingPrice(carpool.getBoarding().getPrice())
                .build();
    }
}
