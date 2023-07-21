package com.kopo.trading.core.dto;

public class PortfolioRecommendResponseDto {
    private String code;
    private String name;
    private Double currentPrice;

    private Double predictPrice;

    public PortfolioRecommendResponseDto(String code, String name, Double currentPrice, Double predictPrice) {
        this.code = code;
        this.name = name;
        this.currentPrice = currentPrice;
        this.predictPrice = predictPrice;
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

    public Double getPredictPrice() {
        return predictPrice;
    }

    public void setPredictPrice(Double predictPrice) {
        this.predictPrice = predictPrice;
    }
}
