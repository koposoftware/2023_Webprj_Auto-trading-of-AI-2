package com.kopo.trading.user.repository;

import com.kopo.trading.user.entity.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberInfoRepository extends JpaRepository<MemberInfo, Long> {

    MemberInfo findMemberInfoByNickname(String nickname);

    MemberInfo findMemberInfoByMemberId(Long memberId);
}
