package com.mate.carpool.domain.notice.aggregate;

import com.mate.carpool.domain.member.aggregate.MemberId;
import com.mate.carpool.domain.shared.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "notice")
@Getter
public class Notice extends BaseTimeEntity {
    @EmbeddedId
    private NoticeId id;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "value", column = @Column(name = "writer_id"))})
    private MemberId writerId;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "text")
    private String content;
}
