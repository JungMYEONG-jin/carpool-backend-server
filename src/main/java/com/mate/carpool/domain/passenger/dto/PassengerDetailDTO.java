package com.mate.carpool.domain.passenger.dto;

import com.mate.carpool.domain.passenger.aggregate.PassengerId;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PassengerDetailDTO {
    private PassengerId passengerId;
    private String profileImageUrl;
    private String username;

    @Builder
    public PassengerDetailDTO(PassengerId passengerId, String profileImageUrl, String username) {
        this.passengerId = passengerId;
        this.profileImageUrl = profileImageUrl;
        this.username = username;
    }
}
