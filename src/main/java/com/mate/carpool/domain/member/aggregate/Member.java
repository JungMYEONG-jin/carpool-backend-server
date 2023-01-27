package com.mate.carpool.domain.member.aggregate;

import com.mate.carpool.domain.shared.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "member")
public class Member extends BaseTimeEntity {
    @EmbeddedId
    private MemberId id;

    @Embedded
    private MemberCredential credential;

    @Column(name = "username")
    private String username;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "member_type")
    private MemberType type;
}