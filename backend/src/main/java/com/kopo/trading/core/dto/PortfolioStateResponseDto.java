package com.kopo.trading.core.dto;

import java.util.Date;

public class PortfolioStateResponseDto {
    private Date date;
    private Double profit;

    public PortfolioStateResponseDto(Date date, Double profit) {
        this.date = date;
        this.profit = profit;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }
}
