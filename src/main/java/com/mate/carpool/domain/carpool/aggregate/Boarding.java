package com.mate.carpool.domain.carpool.aggregate;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
public class Boarding {
    @Column(name = "boarding_place")
    private String place;
    @Column(name = "boarding_price")
    private Integer price;
}
