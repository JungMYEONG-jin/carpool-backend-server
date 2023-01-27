package com.mate.carpool.domain.member.repository;

import com.mate.carpool.domain.member.aggregate.Member;
import com.mate.carpool.domain.member.aggregate.MemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, MemberId> {
    boolean existsByCredentialEmail(String email);
}
