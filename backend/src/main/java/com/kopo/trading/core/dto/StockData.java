package com.kopo.trading.core.dto;

import java.util.Date;

public class StockData {
    private String code;
    private Integer cap;
    private Double changed_rate;
    private Integer high;
    private Integer low;
    private Integer open;
    private Date trading_day;
    private Double volume;

    public StockData(String code, Integer cap, Double changedRate, Integer high, Integer low, Integer open, Date tradingDay, Double volume) {
        this.code = code;
        this.cap = cap;
        this.changed_rate = changedRate;
        this.high = high;
        this.low = low;
        this.open = open;
        this.trading_day = tradingDay;
        this.volume = volume;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCap() {
        return cap;
    }

    public void setCap(Integer cap) {
        this.cap = cap;
    }

    public Double getChanged_rate() {
        return changed_rate;
    }

    public void setChanged_rate(Double changed_rate) {
        this.changed_rate = changed_rate;
    }

    public Integer getHigh() {
        return high;
    }

    public void setHigh(Integer high) {
        this.high = high;
    }

    public Integer getLow() {
        return low;
    }

    public void setLow(Integer low) {
        this.low = low;
    }

    public Integer getOpen() {
        return open;
    }

    public void setOpen(Integer open) {
        this.open = open;
    }

    public Date getTrading_day() {
        return trading_day;
    }

    public void setTrading_day(Date trading_day) {
        this.trading_day = trading_day;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }
}

