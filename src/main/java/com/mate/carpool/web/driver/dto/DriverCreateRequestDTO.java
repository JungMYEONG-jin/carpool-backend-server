package com.mate.carpool.web.driver.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
public class DriverCreateRequestDTO {
    @NotNull
    private String carNumber;

    @NotNull
    @Pattern(regexp = "[0-9]{11}", message = "숫자만 11개 입력하셔야 합니다.")
    private String phoneNumber;
}
