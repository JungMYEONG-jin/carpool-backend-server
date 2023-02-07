package com.mate.carpool.web.passenger;

import com.mate.carpool.domain.passenger.service.PassengerService;
import com.mate.carpool.shared.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Printable;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/passenger")
public class PassengerController {
    private final PassengerService passengerService;

    /**
     * 탑승자가 카풀 탑승
     */
    @PostMapping("/ride/{carpoolId}")
    public ResponseEntity<CommonResponse> ride(
            @PathVariable(name = "carpoolId") String carpoolId,
            Principal principal
    ) {
        String email = principal.getName();
        passengerService.ride(email, carpoolId);
        return ResponseEntity.ok(CommonResponse.of(HttpStatus.OK, "성공적으로 탑승하였습니다."));
    }

    @DeleteMapping("/{passengerId}")
    public ResponseEntity<CommonResponse> delete(
            Principal principal,
            @PathVariable String passengerId
    ){
        String email = principal.getName();
        passengerService.delete(email, passengerId);
        return ResponseEntity.ok(CommonResponse.of(HttpStatus.OK, "성공적으로 예약을 취소했습니다."));
    }
}
