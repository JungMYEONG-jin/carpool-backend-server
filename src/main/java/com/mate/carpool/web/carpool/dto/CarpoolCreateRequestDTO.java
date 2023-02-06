package com.mate.carpool.web.carpool.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mate.carpool.domain.carpool.dto.CarpoolCreateDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CarpoolCreateRequestDTO {
    // 출발 지역
    @NotNull
    private String departureArea;
    // 출발 날짜 및 시간
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departureTime;
    // 도착 지역
    @NotNull
    private String arrivalArea;
    // 탑승 장소
    @NotNull
    private String boardingPlace;
    // 오픈 채팅방 링크
    @NotNull
    private String openChatUrl;
    // 탑승 인원
    @NotNull
    private Integer recruitPerson;
    // 인당 탑승 비용
    @NotNull
    private Integer boardingPrice;

    public CarpoolCreateDTO toCreateDTO() {
        return CarpoolCreateDTO.builder()
                .departureArea(departureArea)
                .departureTime(departureTime)
                .arrivalArea(arrivalArea)
                .boardingPlace(boardingPlace)
                .openChatUrl(openChatUrl)
                .boardingPrice(boardingPrice)
                .recruitPerson(recruitPerson)
                .build();
    }

    @Builder
    public CarpoolCreateRequestDTO(String departureArea, LocalDateTime departureTime, String arrivalArea, String boardingPlace, String openChatUrl, Integer recruitPerson, Integer boardingPrice) {
        this.departureArea = departureArea;
        this.departureTime = departureTime;
        this.arrivalArea = arrivalArea;
        this.boardingPlace = boardingPlace;
        this.openChatUrl = openChatUrl;
        this.recruitPerson = recruitPerson;
        this.boardingPrice = boardingPrice;
    }
}
