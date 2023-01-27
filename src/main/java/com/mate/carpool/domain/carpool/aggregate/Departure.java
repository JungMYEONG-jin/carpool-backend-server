package com.mate.carpool.domain.carpool.aggregate;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

// 출발지에 관한 정보
@Getter
@Embeddable
public class Departure {
    @Column(name = "start_area")
    private String area;
    @Column(name = "start_time")
    private LocalDateTime time;
}
