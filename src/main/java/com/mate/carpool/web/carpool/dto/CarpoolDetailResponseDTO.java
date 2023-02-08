package com.mate.carpool.web.carpool.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mate.carpool.domain.carpool.dto.CarpoolDetailDTO;
import com.mate.carpool.web.driver.dto.DriverDetailDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class CarpoolDetailResponseDTO {
    String carpoolId;
    String departureArea;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime departureTime;
    String arrivalArea;
    String boardingPlace;
    String openChatUrl;
    Integer recruitPerson;
    Integer boardingPrice;
    DriverDetailDTO driver;
    List<PassengerDetailResponseDTO> passengers;

    @Builder
    public CarpoolDetailResponseDTO(
            String carpoolId,
            String departureArea,
            LocalDateTime departureTime,
            String arrivalArea,
            String boardingPlace,
            String openChatUrl,
            Integer recruitPerson,
            Integer boardingPrice,
            DriverDetailDTO driver,
            List<PassengerDetailResponseDTO> passengers) {
        this.carpoolId = carpoolId;
        this.departureArea = departureArea;
        this.departureTime = departureTime;
        this.arrivalArea = arrivalArea;
        this.boardingPlace = boardingPlace;
        this.openChatUrl = openChatUrl;
        this.recruitPerson = recruitPerson;
        this.boardingPrice = boardingPrice;
        this.driver = driver;
        this.passengers = passengers;
    }

    public static CarpoolDetailResponseDTO from(CarpoolDetailDTO dto) {
        return CarpoolDetailResponseDTO.builder()
                .carpoolId(dto.getCarpoolId())
                .departureArea(dto.getDepartureArea())
                .departureTime(dto.getDepartureTime())
                .arrivalArea(dto.getArrivalArea())
                .openChatUrl(dto.getOpenChatUrl())
                .recruitPerson(dto.getRecruitPerson())
                .boardingPrice(dto.getBoardingPrice())
                .boardingPlace(dto.getBoardingPlace())
                .driver(dto.getDriver())
                .passengers(dto.getPassengers().stream()
                        .map(PassengerDetailResponseDTO::from)
                        .collect(Collectors.toList())
                )
                .build();
    }
}
