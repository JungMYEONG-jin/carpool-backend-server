package com.mate.carpool.domain.member.aggregate;

import com.mate.carpool.domain.carpool.aggregate.CarpoolId;
import com.mate.carpool.domain.passenger.aggregate.Passenger;
import com.mate.carpool.domain.passenger.aggregate.PassengerStatus;
import com.mate.carpool.domain.shared.BaseTimeEntity;
import lombok.Builder;
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

    protected Member() {
    }

    @Builder
    public Member(String email, String password, String username, String profileImageUrl, MemberType type) {
        this.id = new MemberId();
        this.credential = new MemberCredential(email, password);
        this.username = username;
        this.profileImageUrl = profileImageUrl;
        this.type = type;
    }

    // domain logic
    public void changeType(MemberType type){
        this.type = type;
    }

    public Passenger createPassenger(CarpoolId carpoolId) {
        return Passenger.builder()
                .status(PassengerStatus.COMMON)
                .memberId(this.id)
                .carpoolId(carpoolId)
                .build();
    }
}