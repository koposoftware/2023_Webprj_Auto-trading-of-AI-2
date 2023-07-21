package com.kopo.trading.core.dto;

import java.util.List;
import java.util.Map;

public class PortfolioSummaryResponseDto {
    private String nickname;
    private Double amount;
    private Map<String, Double> stocks;

    public PortfolioSummaryResponseDto(String nickname, Double amount, Map<String, Double> stocks) {
        this.nickname = nickname;
        this.amount = amount;
        this.stocks = stocks;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Map<String, Double> getStocks() {
        return stocks;
    }

    public void setStocks(Map<String, Double> stocks) {
        this.stocks = stocks;
    }
}
