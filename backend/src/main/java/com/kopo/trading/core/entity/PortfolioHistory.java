package com.kopo.trading.core.entity;

import com.kopo.trading.util.BaseTimeEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PortfolioHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long portfolioId;
    private Long memberId;
    private Long amount;
    private Double avgPrice;
    private String kospiId;

    public PortfolioHistory() {
    }

    public PortfolioHistory(Long memberId, Long amount, Double avgPrice, String kospiId) {
        this.memberId = memberId;
        this.amount = amount;
        this.avgPrice = avgPrice;
        this.kospiId = kospiId;
    }

    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(Double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getKospiId() {
        return kospiId;
    }

    public void setKospiId(String kospiId) {
        this.kospiId = kospiId;
    }
}
