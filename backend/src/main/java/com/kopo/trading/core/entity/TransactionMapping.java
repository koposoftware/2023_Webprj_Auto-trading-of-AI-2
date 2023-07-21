package com.kopo.trading.core.entity;

import com.kopo.trading.user.entity.Member;

public class TransactionMapping {
    String code;
    String name;
    Double pricePerStock;
    Double amount;

    public TransactionMapping(String code, String name, Double pricePerStock, Double amount) {
        this.code = code;
        this.name = name;
        this.pricePerStock = pricePerStock;
        this.amount = amount;
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
}
