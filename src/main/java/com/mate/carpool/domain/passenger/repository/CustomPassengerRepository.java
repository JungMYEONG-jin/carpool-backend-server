package com.mate.carpool.domain.passenger.repository;

import com.mate.carpool.domain.carpool.aggregate.CarpoolId;
import com.mate.carpool.domain.passenger.dto.PassengerDetailDTO;

import java.util.List;

public interface CustomPassengerRepository {
    List<PassengerDetailDTO> getPassengerDetail(CarpoolId carpoolId);
}
