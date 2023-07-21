package com.kopo.trading.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kopo.trading.util.BaseTimeEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/*@Entity: entity로 사용하기 위해서 annotation을 붙이고 PK 지정
 * entity는 @Id를 붙여 PK로 꼭 지정 해줘야 함
 * @GeneratedValue는 ID 값을 entity가 생성될 때마다 자동 생성되게 해주는 annotation
 * */
@Entity
public class Kospi extends BaseTimeEntity {
    @Id
    private String id;
    private String name;
    private Long stockSupply;
    @JsonIgnore
    @OneToMany(mappedBy = "kospi")
    private List<Stock> stocks;
    @JsonIgnore
    @OneToMany(mappedBy = "kospi")
    private List<Transaction> transactions;

    public Kospi() {
    }

    public Kospi(String id, String name, Long stockSupply, List<Stock> stocks, List<Transaction> transactions) {
        this.id = id;
        this.name = name;
        this.stockSupply = stockSupply;
        this.stocks = stocks;
        this.transactions = transactions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStockSupply() {
        return stockSupply;
    }

    public void setStockSupply(Long stockSupply) {
        this.stockSupply = stockSupply;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
