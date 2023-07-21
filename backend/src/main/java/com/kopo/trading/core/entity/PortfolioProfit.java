package com.kopo.trading.core.entity;

import com.kopo.trading.user.entity.Member;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PortfolioProfit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date tradingDate;

    private Double profit;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;


    public PortfolioProfit() {

    }

    public PortfolioProfit(Date tradingDate, Double profit, Member member) {
        this.tradingDate = tradingDate;
        this.profit = profit;
        this.member = member;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTradingDate() {
        return tradingDate;
    }

    public void setTradingDate(Date tradingDate) {
        this.tradingDate = tradingDate;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
