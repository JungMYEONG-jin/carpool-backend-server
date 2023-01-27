package com.mate.carpool.domain.driver.aggregate;

import com.mate.carpool.domain.member.aggregate.MemberId;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "driver")
public class Driver {
    @EmbeddedId
    private DriverId id;

    @Embedded
    private Car car;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Embedded
    private MemberId memberId;
}
