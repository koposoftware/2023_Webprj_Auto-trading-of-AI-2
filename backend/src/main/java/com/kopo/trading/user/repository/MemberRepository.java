package com.kopo.trading.user.repository;

import com.kopo.trading.user.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findMemberByEmailAddress(String emailAddress);

    Member findMemberById(Long id);

}
