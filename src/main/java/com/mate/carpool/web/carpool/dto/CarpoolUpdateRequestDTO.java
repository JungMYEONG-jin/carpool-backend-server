package com.mate.carpool.web.carpool.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mate.carpool.domain.carpool.dto.CarpoolUpdateDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CarpoolUpdateRequestDTO {
    private String carpoolId;
    private String departureArea;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departureTime;
    private String arrivalArea;
    private String boardingPlace;
    private String openChatUrl;
    private Integer recruitPerson;
    private Integer boardingPrice;

    public CarpoolUpdateDTO toUpdateDTO() {
        return CarpoolUpdateDTO.builder()
                .carpoolId(carpoolId)
                .departureArea(departureArea)
                .departureTime(departureTime)
                .arrivalArea(arrivalArea)
                .boardingPlace(boardingPlace)
                .boardingPrice(boardingPrice)
                .openChatUrl(openChatUrl)
                .recruitPerson(recruitPerson)
                .build();
    }
}
