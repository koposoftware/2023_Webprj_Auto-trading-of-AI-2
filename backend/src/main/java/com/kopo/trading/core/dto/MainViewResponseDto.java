package com.kopo.trading.core.dto;

import javax.persistence.Column;

public class MainViewResponseDto {
    private String category;

    private String Price;

    private String status;

    public MainViewResponseDto(String category, String price, String status) {
        this.category = category;
        Price = price;
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
