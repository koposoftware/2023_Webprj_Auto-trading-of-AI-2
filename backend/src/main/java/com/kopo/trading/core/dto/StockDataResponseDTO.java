package com.kopo.trading.core.dto;

public class StockDataResponseDTO {
    private String kospiName;
    private String code;
    private String close;
    private String changedRate;

    public StockDataResponseDTO(String kospiName, String code, String close, String changedRate) {
        this.kospiName = kospiName;
        this.code = code;
        this.close = close;
        this.changedRate = changedRate;
    }

    public String getKospiName() {
        return kospiName;
    }

    public void setKospiName(String kospiName) {
        this.kospiName = kospiName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getChangedRate() {
        return changedRate;
    }

    public void setChangedRate(String changedRate) {
        this.changedRate = changedRate;
    }
}
