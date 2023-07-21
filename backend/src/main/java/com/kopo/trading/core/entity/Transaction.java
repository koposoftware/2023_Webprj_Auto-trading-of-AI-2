package com.kopo.trading.core.entity;

import com.kopo.trading.user.entity.Member;
import com.kopo.trading.util.BaseTimeEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Transaction extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    private Double amount;
    private Date tradeDate;
    private Double pricePerStock;
    private Double totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    // 엔티티를 조회할 때, 연관된 엔티티는 실제 사용 시점에 조회한다.
    // 즉, 실제 객체가 사용되는 시점까지 조회를 미룬다.
    private Kospi kospi;

    public Transaction() {
    }

    public Transaction(Double amount, Date tradeDate, Double pricePerStock, Double totalPrice, Member member, Kospi kospi) {
        this.amount = amount;
        this.tradeDate = tradeDate;
        this.pricePerStock = pricePerStock;
        this.totalPrice = totalPrice;
        this.member = member;
        this.kospi = kospi;
    }

    public Transaction(Long amount, Date date, Double close, double v, String code) {
        super();
    }


    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Double getPricePerStock() {
        return pricePerStock;
    }

    public void setPricePerStock(Double pricePerStock) {
        this.pricePerStock = pricePerStock;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Kospi getKospi() {
        return kospi;
    }

    public void setKospi(Kospi kospi) {
        this.kospi = kospi;
    }
}
