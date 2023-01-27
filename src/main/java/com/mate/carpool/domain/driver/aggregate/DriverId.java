package com.mate.carpool.domain.driver.aggregate;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Embeddable
public class DriverId implements Serializable {
    @Column(name = "driver_id")
    private String uuid;

    public String get() {
        return uuid;
    }

    public void generate() {
        uuid = UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriverId driverId = (DriverId) o;
        return Objects.equals(uuid, driverId.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
