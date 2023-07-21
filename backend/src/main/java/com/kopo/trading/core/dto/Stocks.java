package com.kopo.trading.core.dto;

import java.util.List;

public class Stocks {
    private List<StockData> info;

    public Stocks(List<StockData> info) {
        this.info = info;
    }

    public List<StockData> getInfo() {
        return info;
    }

    public void setInfo(List<StockData> info) {
        this.info = info;
    }
}
