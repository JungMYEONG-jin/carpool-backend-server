package com.mate.carpool.domain.carpool.aggregate;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Embeddable
public class CarpoolId implements Serializable {
    @Column(name = "carpool_id")
    private String value;

    public void generate(){
        value = UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarpoolId carpoolId = (CarpoolId) o;
        return Objects.equals(value, carpoolId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public CarpoolId(String value) {
        this.value = value;
    }

    public CarpoolId() {
    }
}
