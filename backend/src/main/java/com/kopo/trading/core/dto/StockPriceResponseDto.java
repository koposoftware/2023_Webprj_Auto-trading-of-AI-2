package com.kopo.trading.core.dto;

import java.util.List;

public class StockPriceResponseDto {
    private String code;
    private String name;
    private List<CurrentStockPriceDto> prices;

    public StockPriceResponseDto(String code, String name, List<CurrentStockPriceDto> prices) {
        this.code = code;
        this.name = name;
        this.prices = prices;
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

    public List<CurrentStockPriceDto> getPrices() {
        return prices;
    }

    public void setPrices(List<CurrentStockPriceDto> prices) {
        this.prices = prices;
    }
}
