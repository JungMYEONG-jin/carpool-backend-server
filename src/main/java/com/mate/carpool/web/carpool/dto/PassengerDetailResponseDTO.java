package com.mate.carpool.web.carpool.dto;


import com.mate.carpool.domain.passenger.dto.PassengerDetailDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PassengerDetailResponseDTO {
    private String passengerId;
    private String profileImageUrl;
    private String username;

    @Builder
    public PassengerDetailResponseDTO(String passengerId, String profileImageUrl, String username) {
        this.passengerId = passengerId;
        this.profileImageUrl = profileImageUrl;
        this.username = username;
    }

    public static PassengerDetailResponseDTO from(PassengerDetailDTO dto) {
        return PassengerDetailResponseDTO.builder()
                .passengerId(dto.getPassengerId().getValue())
                .username(dto.getUsername())
                .profileImageUrl(dto.getProfileImageUrl())
                .build();
    }
}
