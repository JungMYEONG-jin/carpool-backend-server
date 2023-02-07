package com.mate.carpool.domain.carpool.repository;

import com.mate.carpool.domain.carpool.aggregate.Carpool;
import com.mate.carpool.domain.carpool.aggregate.CarpoolId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

public interface CarpoolRepository extends JpaRepository<Carpool, CarpoolId>, CarpoolRepositoryCustom {
}
