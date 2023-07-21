package com.kopo.trading.core.dto;


import java.util.Date;

public class CurrentStockPriceDto {
    private String time;
    private Double high;
    private Double low;
    private Double open;
    private Double close;

    private Double volume;

    public CurrentStockPriceDto(String time, Double high, Double low, Double open, Double close, Double volume) {
        this.time = time;
        this.high = high;
        this.low = low;
        this.open = open;
        this.close = close;
        this.volume = volume;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
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

    @Override
    public String toString() {
        return "CurrentStockPriceDto{" +
                "time='" + time + '\'' +
                ", high=" + high +
                ", low=" + low +
                ", open=" + open +
                ", close=" + close +
                ", volume=" + volume +
                '}';
    }
}
