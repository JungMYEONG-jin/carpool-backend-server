package com.mate.carpool.domain.driver.dto;

import com.mate.carpool.domain.driver.aggregate.Driver;
import com.mate.carpool.domain.member.aggregate.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DriverCreateDTO {
    private String carNumber;
    private String phoneNumber;


    @Builder
    public DriverCreateDTO(String carNumber, String phoneNumber) {
        this.carNumber = carNumber;
        this.phoneNumber = phoneNumber;
    }

    public Driver toEntity(Member member, String carImageUrl) {
        return Driver.builder()
                .carNumber(carNumber)
                .carImageUrl(carImageUrl)
                .phoneNumber(phoneNumber)
                .memberId(member.getId())
                .build();
    }
}
