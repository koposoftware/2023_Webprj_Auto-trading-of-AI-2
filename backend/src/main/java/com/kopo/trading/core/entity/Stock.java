package com.kopo.trading.core.entity;

import com.kopo.trading.util.BaseTimeEntity;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Stock extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date tradingDay;
    // Long은 null 가능
    @Column(nullable = false)
    private Double open;
    @Column(nullable = false)
    private Double high;
    @Column(nullable = false)
    private Double low;
    @Column(nullable = false)
    private Double close;
    @Column(nullable = false)
    private Double volume;
    @Column(nullable = false)
    private Double cap; // 시가 총액
    @Column(nullable = false)
    @ColumnDefault("0")
    private Double changedRate; // 등락률

    @ManyToOne(fetch = FetchType.LAZY) // Kospi는 하나. Stock 여러개
    private Kospi kospi;

    public Stock() {
    }

    public Stock(Date tradingDay, Double open, Double high, Double low, Double close, Double volume, Double cap, Double changedRate, Kospi kospi) {
        this.tradingDay = tradingDay;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.cap = cap;
        this.changedRate = changedRate;
        this.kospi = kospi;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTradingDay() {
        return tradingDay;
    }

    public void setTradingDay(Date tradingDay) {
        this.tradingDay = tradingDay;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getCap() {
        return cap;
    }

    public void setCap(Double cap) {
        this.cap = cap;
    }

    public Double getChangedRate() {
        return changedRate;
    }

    public void setChangedRate(Double changedRate) {
        this.changedRate = changedRate;
    }

    public Kospi getKospi() {
        return kospi;
    }

    public void setKospi(Kospi kospi) {
        this.kospi = kospi;
    }
}