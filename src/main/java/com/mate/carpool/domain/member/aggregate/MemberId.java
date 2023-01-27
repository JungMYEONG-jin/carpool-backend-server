package com.mate.carpool.domain.member.aggregate;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Embeddable
public class MemberId implements Serializable {
    @Column(name = "member_id")
    private String value;

    public String get() {
        return value;
    }

    public void generate() {
        value = UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberId memberId = (MemberId) o;
        return value.equals(memberId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
