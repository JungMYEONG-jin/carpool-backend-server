package com.mate.carpool.domain.passenger.aggregate;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Embeddable
public class PassengerId implements Serializable {
    @Column(name = "passenger_id")
    private String value;

    public PassengerId() {
    }

    public PassengerId(String value) {
        this.value = value;
    }

    public void generate() {
        value = UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PassengerId that = (PassengerId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
