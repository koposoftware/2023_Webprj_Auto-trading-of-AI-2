package com.kopo.trading.core.dto;


public class PredictResponseDto {

    private String code;
    private Double predict_close;

    public PredictResponseDto(String code, Double predict_close) {
        this.code = code;
        this.predict_close = predict_close;
    }

    public PredictResponseDto() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getPredict_close() {
        return predict_close;
    }

    public void setPredict_close(Double predict_close) {
        this.predict_close = predict_close;
    }
}
