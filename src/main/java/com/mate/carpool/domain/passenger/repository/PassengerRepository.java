package com.mate.carpool.domain.passenger.repository;


import com.mate.carpool.domain.carpool.aggregate.CarpoolId;
import com.mate.carpool.domain.passenger.aggregate.Passenger;
import com.mate.carpool.domain.passenger.aggregate.PassengerId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, PassengerId> {
    List<Passenger> findAllByCarpoolId(CarpoolId carpoolId);
}
