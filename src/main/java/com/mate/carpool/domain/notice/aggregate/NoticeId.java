package com.mate.carpool.domain.notice.aggregate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class NoticeId implements Serializable {
    @Column(name = "notice_id")
    private String value;

    public void generate(){
        this.value = UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoticeId noticeId = (NoticeId) o;
        return Objects.equals(value, noticeId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
