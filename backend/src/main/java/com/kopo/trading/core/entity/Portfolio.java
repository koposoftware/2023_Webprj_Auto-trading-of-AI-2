package com.kopo.trading.core.entity;

import com.kopo.trading.core.constants.Trading;
import com.kopo.trading.user.entity.Member;
import com.kopo.trading.util.BaseTimeEntity;

import javax.persistence.*;
import javax.sound.sampled.Port;

@Entity
public class Portfolio extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long portfolioId;

    @Column(nullable = false)
    private double amount;
    private Double avgPrice;
    private String kospiId;

    private Double target;
    private Double loss;

    private Trading trading;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public Portfolio() {
    }

    public Portfolio(Double amount, Double avgPrice, String kospiId, Double target, Double loss, Trading trading, Member member) {
        this.amount = amount;
        this.avgPrice = avgPrice;
        this.kospiId = kospiId;
        this.target = target;
        this.loss = loss;
        this.trading = trading;
        this.member = member;
    }

    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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

    public Double getTarget() {
        return target;
    }

    public void setTarget(Double target) {
        this.target = target;
    }

    public Double getLoss() {
        return loss;
    }

    public void setLoss(Double loss) {
        this.loss = loss;
    }

    public Trading getTrading() {
        return trading;
    }

    public void setTrading(Trading trading) {
        this.trading = trading;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
