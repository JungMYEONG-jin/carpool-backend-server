package com.mate.carpool.web.carpool.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mate.carpool.domain.carpool.dto.CarpoolShortDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CarpoolShortResponseDTO {
    private String carpoolId;
    private String departureArea;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departureTime;
    private String driverImageUrl;
    private Integer currentPerson;
    private Integer recruitPerson;
    private Integer boardingPrice;


    @Builder
    public CarpoolShortResponseDTO(String carpoolId, String departureArea, LocalDateTime departureTime, String driverImageUrl, Integer currentPerson, Integer recruitPerson, Integer boardingPrice) {
        this.carpoolId = carpoolId;
        this.departureArea = departureArea;
        this.departureTime = departureTime;
        this.driverImageUrl = driverImageUrl;
        this.currentPerson = currentPerson;
        this.recruitPerson = recruitPerson;
        this.boardingPrice = boardingPrice;
    }

    public static CarpoolShortResponseDTO from(CarpoolShortDTO dto) {
        return CarpoolShortResponseDTO.builder()
                .carpoolId(dto.getCarpoolId())
                .departureArea(dto.getDepartureArea())
                .departureTime(dto.getDepartureTime())
                .driverImageUrl(dto.getDriverImageUrl())
                .recruitPerson(dto.getRecruitPerson())
                .currentPerson(dto.getCurrentPerson())
                .boardingPrice(dto.getBoardingPrice())
                .build();
    }
}
