package com.kopo.trading.core.dto;

public class StockListResponseDto {
    private String code;
    private String name;
    private Double currentPrice;
    private Double change;

    public StockListResponseDto(String code, String name, Double currentPrice, Double change) {
        this.code = code;
        this.name = name;
        this.currentPrice = currentPrice;
        this.change = change;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Double getChange() {
        return change;
    }

    public void setChange(Double change) {
        this.change = change;
    }
}
