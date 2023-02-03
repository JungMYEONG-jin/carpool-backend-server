package com.mate.carpool.domain.driver.aggregate;

import com.mate.carpool.domain.driver.dto.DriverCreateDTO;
import com.mate.carpool.domain.member.aggregate.Member;
import com.mate.carpool.domain.member.aggregate.MemberId;
import lombok.Builder;
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


    protected Driver() {
    }

    @Builder
    public Driver(String carNumber, String carImageUrl, String phoneNumber, MemberId memberId) {
        this.id = new DriverId();
        this.car = new Car(carNumber, carImageUrl);
        this.phoneNumber = phoneNumber;
        this.memberId = memberId;
    }
}
