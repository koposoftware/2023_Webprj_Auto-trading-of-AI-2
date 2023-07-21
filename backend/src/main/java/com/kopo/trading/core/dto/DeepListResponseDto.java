package com.kopo.trading.core.dto;

import java.time.LocalDateTime;

public class DeepListResponseDto {
    private LocalDateTime time;
    private String name;
    private String code;
    private Double close;
    private Double predClose;
    private Double diff;

    public DeepListResponseDto(LocalDateTime time, String name, String code, Double close, Double predClose, Double diff) {
        this.time = time;
        this.name = name;
        this.code = code;
        this.close = close;
        this.predClose = predClose;
        this.diff = diff;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getPredClose() {
        return predClose;
    }

    public void setPredClose(Double predClose) {
        this.predClose = predClose;
    }

    public Double getDiff() {
        return diff;
    }

    public void setDiff(Double diff) {
        this.diff = diff;
    }
}
