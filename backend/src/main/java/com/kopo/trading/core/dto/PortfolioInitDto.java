package com.kopo.trading.core.dto;

import com.kopo.trading.core.constants.Trading;

import java.util.List;
import java.util.Map;

public class PortfolioInitDto {
    private Trading trading;
    private List<String> stocks;

    private Double target;
    private Double loss;

    private Double seed;

    public PortfolioInitDto(Trading trading, List<String> stocks, Double target, Double loss, Double seed) {
        this.trading = trading;
        this.stocks = stocks;
        this.target = target;
        this.loss = loss;
        this.seed = seed;
    }

    public Trading getTrading() {
        return trading;
    }

    public void setTrading(Trading trading) {
        this.trading = trading;
    }

    public List<String> getStocks() {
        return stocks;
    }

    public void setStocks(List<String> stocks) {
        this.stocks = stocks;
    }

    public Double getTarget() {
        return target;
    }

    public void setTarget(Double target) {
        this.target = target;
    }

    public Double getLoss() {
        return loss;
    }

    public void setLoss(Double loss) {
        this.loss = loss;
    }

    public Double getSeed() {
        return seed;
    }

    public void setSeed(Double seed) {
        this.seed = seed;
    }
}
