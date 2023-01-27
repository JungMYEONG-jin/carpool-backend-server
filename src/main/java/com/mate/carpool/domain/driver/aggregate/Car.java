package com.mate.carpool.domain.driver.aggregate;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Car {
    @Column(name = "car_number")
    private String number;

    @Column(name = "car_image_url")
    private String imageUrl;
}
