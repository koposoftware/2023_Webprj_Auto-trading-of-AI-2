package com.kopo.trading.core.repository;

import com.kopo.trading.core.entity.PortfolioProfit;
import com.kopo.trading.user.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PortfolioProfitRepository extends JpaRepository<PortfolioProfit, Long> {

    List<PortfolioProfit> findAllByMember(Member member);
}
