package com.kopo.trading.core.dto;


import java.util.Date;

public class StockResponseDto {
    private String name; // 종목명
    private String code; // 종목코드
    private Date trading_day; // 거래일자
    private Double close; // 종가
    private Double open; // 시가
    private Double high; // 고가
    private Double low; // 저가
    private Double volume; // 거래량
    private Double cap; // 시가총액

    public StockResponseDto() {
    }

    public StockResponseDto(String name, String code, Date trading_day, Double close, Double open, Double high, Double low, Double volume, Double cap) {
        this.name = name;
        this.code = code;
        this.trading_day = trading_day;
        this.close = close;
        this.open = open;
        this.high = high;
        this.low = low;
        this.volume = volume;
        this.cap = cap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getTrading_day() {
        return trading_day;
    }

    public void setTrading_day(Date trading_day) {
        this.trading_day = trading_day;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
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
}
