package com.mate.carpool.domain.member.repository;

import com.mate.carpool.domain.member.aggregate.Member;
import com.mate.carpool.domain.member.aggregate.MemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, MemberId> {
    boolean existsByCredentialEmail(String email);
    Optional<Member> findByCredentialEmail(String email);
}
