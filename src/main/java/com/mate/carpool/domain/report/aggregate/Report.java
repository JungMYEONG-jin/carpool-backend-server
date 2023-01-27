package com.mate.carpool.domain.report.aggregate;

import com.mate.carpool.domain.carpool.aggregate.CarpoolId;
import com.mate.carpool.domain.member.aggregate.MemberId;
import com.mate.carpool.domain.shared.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "report")
public class Report extends BaseTimeEntity {
    @EmbeddedId
    private ReportId id;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "value", column = @Column(name = "reporter_id"))})
    private MemberId reporterId;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "value", column = @Column(name = "reported_id"))})
    private MemberId reportedId;

    @Embedded
    private CarpoolId carpoolId;

    @Column(name = "content", columnDefinition = "text")
    private String content;
}
