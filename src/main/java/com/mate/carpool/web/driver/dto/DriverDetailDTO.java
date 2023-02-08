package com.mate.carpool.web.driver.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DriverDetailDTO {
    private String carImageUrl;
    private String carNumber;
    private String name;
    private String profileImageUrl;

    @Builder
    public DriverDetailDTO(String carImageUrl, String carNumber, String name, String profileImageUrl) {
        this.carImageUrl = carImageUrl;
        this.carNumber = carNumber;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }
}
