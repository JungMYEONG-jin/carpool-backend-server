package com.mate.carpool.domain.carpool.aggregate;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
public class Arrival {
    @Column(name = "end_area")
    private String area;
}
