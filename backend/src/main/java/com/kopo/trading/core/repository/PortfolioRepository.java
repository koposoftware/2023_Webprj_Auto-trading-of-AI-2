package com.kopo.trading.core.repository;

import com.kopo.trading.core.entity.Portfolio;
import com.kopo.trading.user.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findAllByMemberId(Long memberId);

    void deleteByMember(Member member);


}
