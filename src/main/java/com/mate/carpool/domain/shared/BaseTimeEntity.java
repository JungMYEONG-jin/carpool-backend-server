package com.mate.carpool.domain.shared;


import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {
    @CreatedDate
    @Column(nullable = false, updatable = false, name = "created_at")
    protected LocalDateTime createdAt;
    @LastModifiedDate
    @Column(nullable = false, name = "modified_at")
    protected LocalDateTime modifiedAt;

    @Column(name = "deleted_at")
    protected LocalDateTime deletedAt;
}
