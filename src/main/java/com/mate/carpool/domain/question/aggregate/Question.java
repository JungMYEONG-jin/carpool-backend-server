package com.mate.carpool.domain.question.aggregate;

import com.mate.carpool.domain.member.aggregate.MemberId;
import com.mate.carpool.domain.shared.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "question")
@Getter
public class Question extends BaseTimeEntity {
    @EmbeddedId
    private QuestionId id;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "value", column = @Column(name = "writer_id"))})
    private MemberId writerId;

    @Column(name = "content", columnDefinition = "text")
    private String content;
}
