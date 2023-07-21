package com.kopo.trading.core.dto;

public class
PortFolioListResponseDto {
    private String code;
    private String name; // 종목명
    private Double currentPrice;
    private Double avg;
    private Double profit;

    public PortFolioListResponseDto(String code, String name, Double currentPrice, Double avg, Double profit) {
        this.code = code;
        this.name = name;
        this.currentPrice = currentPrice;
        this.avg = avg;
        this.profit = profit;
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

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }
}
