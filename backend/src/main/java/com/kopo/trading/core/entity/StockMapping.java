package com.kopo.trading.core.entity;

import java.util.Date;

public class StockMapping {
    private String kospiId;
    private Date tradingDay;
    private Double close;

    public StockMapping(String kospiId, Date tradingDay, Double close) {
        this.kospiId = kospiId;
        this.tradingDay = tradingDay;
        this.close = close;
    }

    public String getKospiId() {
        return kospiId;
    }

    public void setKospiId(String kospiId) {
        this.kospiId = kospiId;
    }

    public Date getTradingDay() {
        return tradingDay;
    }

    public void setTradingDay(Date tradingDay) {
        this.tradingDay = tradingDay;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }
}
