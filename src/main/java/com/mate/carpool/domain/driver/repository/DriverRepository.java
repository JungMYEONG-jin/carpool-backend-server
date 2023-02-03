package com.mate.carpool.domain.driver.repository;

import com.mate.carpool.domain.driver.aggregate.Driver;
import com.mate.carpool.domain.driver.aggregate.DriverId;
import com.mate.carpool.domain.member.aggregate.MemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, DriverId> {
    boolean existsByMemberId(MemberId memberId);
}
