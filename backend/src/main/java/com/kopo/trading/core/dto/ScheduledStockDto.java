package com.kopo.trading.core.dto;

import com.kopo.trading.core.entity.Stock;

import static com.kopo.trading.util.Utils.convertStringToDate;

public class ScheduledStockDto {
    private String code;
    private String trading_day;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double volume;
    private Double changed_rate;

    public ScheduledStockDto(String code, String trading_day, Double open, Double high, Double low, Double close, Double volume, Double changed_rate) {
        this.code = code;
        this.trading_day = trading_day;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.changed_rate = changed_rate;
    }

    public ScheduledStockDto() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTrading_day() {
        return trading_day;
    }

    public void setTrading_day(String trading_day) {
        this.trading_day = trading_day;
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

    public Double getChanged_rate() {
        return changed_rate;
    }

    public void setChanged_rate(Double changed_rate) {
        this.changed_rate = changed_rate;
    }

    public Stock toEntity() {
        return new Stock(convertStringToDate(trading_day, "yyyy-MM-dd" ), open, high, low, close, volume, 0.0, changed_rate, null);
    }
}
