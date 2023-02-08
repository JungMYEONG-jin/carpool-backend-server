package com.mate.carpool.domain.carpool.dto;

import com.mate.carpool.domain.carpool.aggregate.Carpool;
import com.mate.carpool.domain.driver.aggregate.Driver;
import com.mate.carpool.domain.member.aggregate.Member;
import com.mate.carpool.web.driver.dto.DriverDetailDTO;
import com.mate.carpool.domain.passenger.dto.PassengerDetailDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class CarpoolDetailDTO {

    String carpoolId;
    String departureArea;
    LocalDateTime departureTime;
    String arrivalArea;
    String boardingPlace;
    String openChatUrl;
    Integer recruitPerson;
    Integer boardingPrice;
    DriverDetailDTO driver;
    List<PassengerDetailDTO> passengers;

    @Builder
    public CarpoolDetailDTO(String carpoolId, String departureArea, LocalDateTime departureTime, String arrivalArea, String boardingPlace, String openChatUrl, Integer recruitPerson, Integer boardingPrice, DriverDetailDTO driver, List<PassengerDetailDTO> passengers) {
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

    public static CarpoolDetailDTO create(
            Carpool carpool,
            Member member,
            Driver driver,
            List<PassengerDetailDTO> passengers
    ) {
        return CarpoolDetailDTO.builder()
                .carpoolId(carpool.getId().getValue())
                .departureArea(carpool.getDeparture().getArea())
                .departureTime(carpool.getDeparture().getTime())
                .arrivalArea(carpool.getArrival().getArea())
                .boardingPlace(carpool.getBoarding().getPlace())
                .openChatUrl(carpool.getOpenChatUrl())
                .recruitPerson(carpool.getRecruitPerson())
                .boardingPrice(carpool.getBoarding().getPrice())
                .driver(DriverDetailDTO.builder()
                        .carImageUrl(driver.getCar().getImageUrl())
                        .carNumber(driver.getCar().getNumber())
                        .name(member.getUsername())
                        .profileImageUrl(member.getProfileImageUrl())
                        .build()
                )
                .passengers(passengers)
                .build();
    }

}
