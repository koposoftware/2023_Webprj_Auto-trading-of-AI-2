package com.kopo.trading.core.dto;

import java.util.Date;

public class TransactionResponseDto {
    private Date tradeDate;
    private String code;
    private String name;
    private Double pricePerStock;
    private Double amount;
    private Double totalPrice;

    public TransactionResponseDto(Date tradeDate, String code, String name, Double pricePerStock, Double amount, Double totalPrice) {
        this.tradeDate = tradeDate;
        this.code = code;
        this.name = name;
        this.pricePerStock = pricePerStock;
        this.amount = amount;
        this.totalPrice = totalPrice;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
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

    public Double getPricePerStock() {
        return pricePerStock;
    }

    public void setPricePerStock(Double pricePerStock) {
        this.pricePerStock = pricePerStock;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
